package com.example.pknk.service.clinic;

import com.example.pknk.configuration.VNPAYConfig;
import com.example.pknk.domain.dto.request.clinic.CostPaymentUpdateRequest;
import com.example.pknk.domain.dto.request.clinic.VNPayCallbackRequest;
import com.example.pknk.domain.dto.response.clinic.CostResponse;
import com.example.pknk.domain.entity.clinic.Cost;
import com.example.pknk.domain.entity.user.User;
import com.example.pknk.exception.AppException;
import com.example.pknk.exception.ErrorCode;
import com.example.pknk.repository.clinic.CostRepository;
import com.example.pknk.repository.clinic.ExaminationRepository;
import com.example.pknk.repository.clinic.TreatmentPhasesRepository;
import com.example.pknk.repository.user.UserRepository;
import com.example.pknk.util.VNPayUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)
@Slf4j
public class CostService {
    CostRepository costRepository;
    UserRepository userRepository;
    TreatmentPhasesRepository treatmentPhasesRepository;
    ExaminationRepository examinationRepository;
    VNPAYConfig vnPayConfig;

    @PreAuthorize("hasRole('PATIENT')")
    public List<CostResponse> getAllMyCost(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            log.error("Username: {} không tồn tại, Lấy danh sách kết quả thất bại.", username);
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        });

        String patientId = user.getPatient().getId();
        List<Cost> listCost = new ArrayList<>(costRepository.findAllByPatientId(patientId));

        // Find examinations that don't have cost records yet
        // Get all examinations for this patient's appointments
        var examinations = examinationRepository.findAll().stream()
                .filter(exam -> exam.getAppointment() != null 
                        && exam.getAppointment().getPatient() != null
                        && exam.getAppointment().getPatient().getId().equals(patientId)
                        && exam.getTotalCost() > 0)
                .filter(exam -> !costRepository.existsById(exam.getId()))
                .toList();

        // Create cost records for examinations that don't have one
        for (var examination : examinations) {
            if (examination.getAppointment() != null && examination.getAppointment().getPatient() != null) {
                Cost cost = Cost.builder()
                        .id(examination.getId())
                        .title("Khám theo lịch hẹn: " + examination.getAppointment().getDateTime())
                        .status("wait")
                        .totalCost(examination.getTotalCost())
                        .type("examination")
                        .listDentalServiceEntityOrder(examination.getListDentalServicesEntityOrder())
                        .listPrescriptionOrder(examination.getListPrescriptionOrder())
                        .patient(examination.getAppointment().getPatient())
                        .build();
                
                costRepository.save(cost);
                listCost.add(cost);
                log.info("Đã tạo cost record id: {} từ examination id: {}", cost.getId(), examination.getId());
            }
        }

        // Find treatment phases that don't have cost records yet
        var treatmentPhases = treatmentPhasesRepository.findAll().stream()
                .filter(phase -> phase.getTreatmentPlans() != null
                        && phase.getTreatmentPlans().getPatient() != null
                        && phase.getTreatmentPlans().getPatient().getId().equals(patientId)
                        && phase.getCost() > 0)
                .filter(phase -> !costRepository.existsById(phase.getId()))
                .toList();

        // Create cost records for treatment phases that don't have one
        for (var treatmentPhase : treatmentPhases) {
            if (treatmentPhase.getTreatmentPlans() != null && treatmentPhase.getTreatmentPlans().getPatient() != null) {
                Cost cost = Cost.builder()
                        .id(treatmentPhase.getId())
                        .title("Tiến trình điều trị: " + treatmentPhase.getPhaseNumber() + " - " + 
                               (treatmentPhase.getDescription() != null ? treatmentPhase.getDescription() : ""))
                        .status("wait")
                        .totalCost(treatmentPhase.getCost())
                        .type("phase_payment")
                        .treatmentPlan(treatmentPhase.getTreatmentPlans())
                        .listDentalServiceEntityOrder(treatmentPhase.getListDentalServiceEntityOrder())
                        .listPrescriptionOrder(treatmentPhase.getListPrescriptionOrder())
                        .patient(treatmentPhase.getTreatmentPlans().getPatient())
                        .build();
                
                costRepository.save(cost);
                listCost.add(cost);
                log.info("Đã tạo cost record id: {} từ treatment phase id: {}", cost.getId(), treatmentPhase.getId());
            }
        }

        return listCost.stream().map(cost -> CostResponse.builder()
                .id(cost.getId())
                .title(cost.getTitle())
                .paymentMethod(cost.getPaymentMethod())
                .status(cost.getStatus())
                .totalCost(cost.getTotalCost())
                .paymentDate(cost.getPaymentDate())
                .vnpTxnRef(cost.getVnpTxnRef())
                .type(cost.getType() != null ? cost.getType() : "phase_payment") // Default for backward compatibility
                .treatmentPlanId(cost.getTreatmentPlan() != null ? cost.getTreatmentPlan().getId() : null)
                .listDentalServiceEntityOrder(cost.getListDentalServiceEntityOrder())
                .listPrescriptionOrder(cost.getListPrescriptionOrder())
                .build()
        ).toList();
    }

    @PreAuthorize("hasAnyAuthority('UPDATE_PAYMENT_COST','ADMIN')")
    public CostResponse updatePaymentCost(String costId, CostPaymentUpdateRequest request){
        // Try to find cost record first
        Cost cost = costRepository.findById(costId).orElse(null);
        
        // If cost doesn't exist, try to create it from treatment phase
        if (cost == null) {
            log.warn("Cost id: {} không tồn tại, thử tạo từ treatment phase...", costId);
            
            // Check if this is a treatment phase ID
            var treatmentPhase = treatmentPhasesRepository.findById(costId).orElse(null);
            if (treatmentPhase != null && treatmentPhase.getTreatmentPlans() != null) {
                // Create cost record from treatment phase
                cost = Cost.builder()
                        .id(treatmentPhase.getId())
                        .title("Tiến trình điều trị: " + treatmentPhase.getPhaseNumber() + " - " + 
                               (treatmentPhase.getDescription() != null ? treatmentPhase.getDescription() : ""))
                        .status("wait")
                        .totalCost(treatmentPhase.getCost())
                        .type("phase_payment")
                        .treatmentPlan(treatmentPhase.getTreatmentPlans())
                        .listDentalServiceEntityOrder(treatmentPhase.getListDentalServiceEntityOrder())
                        .listPrescriptionOrder(treatmentPhase.getListPrescriptionOrder())
                        .patient(treatmentPhase.getTreatmentPlans().getPatient())
                        .build();
                
                costRepository.save(cost);
                log.info("Đã tạo cost record id: {} từ treatment phase id: {}", cost.getId(), costId);
            } else {
                log.error("Chi phí id: {} không tồn tại và không phải là treatment phase id, cập nhật thanh toán thất bại.", costId);
                throw new AppException(ErrorCode.COST_NOT_EXISTED);
            }
        }

        cost.setPaymentDate(LocalDate.now());
        cost.setPaymentMethod(request.getPaymentMethod());
        cost.setVnpTxnRef(request.getVnpTxnRef());
        cost.setStatus(request.getStatus());
        
        costRepository.save(cost);

        return CostResponse.builder()
                .id(cost.getId())
                .title(cost.getTitle())
                .paymentMethod(cost.getPaymentMethod())
                .status(cost.getStatus())
                .totalCost(cost.getTotalCost())
                .paymentDate(cost.getPaymentDate())
                .vnpTxnRef(cost.getVnpTxnRef())
                .type(cost.getType() != null ? cost.getType() : "phase_payment")
                .treatmentPlanId(cost.getTreatmentPlan() != null ? cost.getTreatmentPlan().getId() : null)
                .listDentalServiceEntityOrder(cost.getListDentalServiceEntityOrder())
                .listPrescriptionOrder(cost.getListPrescriptionOrder())
                .build();
    }

    public CostResponse getCostById(String costId){
        Cost cost = costRepository.findById(costId).orElseThrow(() -> new AppException(ErrorCode.COST_NOT_EXISTED));

        return CostResponse.builder()
                .id(cost.getId())
                .title(cost.getTitle())
                .paymentMethod(cost.getPaymentMethod())
                .status(cost.getStatus())
                .totalCost(cost.getTotalCost())
                .paymentDate(cost.getPaymentDate())
                .vnpTxnRef(cost.getVnpTxnRef())
                .type(cost.getType() != null ? cost.getType() : "phase_payment")
                .treatmentPlanId(cost.getTreatmentPlan() != null ? cost.getTreatmentPlan().getId() : null)
                .listDentalServiceEntityOrder(cost.getListDentalServiceEntityOrder())
                .listPrescriptionOrder(cost.getListPrescriptionOrder())
                .build();
    }

    /**
     * Update payment cost from VNPay callback (public endpoint, no auth required)
     * Validates VNPay secure hash and updates cost record
     */
    public CostResponse updatePaymentCostFromVNPay(VNPayCallbackRequest request) {
        log.info("VNPay callback received for costId: {}", request.getCostId());
        log.info("VNPay params: vnp_ResponseCode={}, vnp_TransactionStatus={}, vnp_Amount={}", 
                request.getVnp_ResponseCode(), request.getVnp_TransactionStatus(), request.getVnp_Amount());

        // Validate VNPay secure hash
        Map<String, String> vnpParamsMap = new HashMap<>();
        vnpParamsMap.put("vnp_Amount", request.getVnp_Amount());
        vnpParamsMap.put("vnp_BankCode", request.getVnp_BankCode());
        vnpParamsMap.put("vnp_BankTranNo", request.getVnp_BankTranNo());
        vnpParamsMap.put("vnp_CardType", request.getVnp_CardType());
        vnpParamsMap.put("vnp_OrderInfo", request.getVnp_OrderInfo());
        vnpParamsMap.put("vnp_PayDate", request.getVnp_PayDate());
        vnpParamsMap.put("vnp_ResponseCode", request.getVnp_ResponseCode());
        vnpParamsMap.put("vnp_TmnCode", request.getVnp_TmnCode());
        vnpParamsMap.put("vnp_TransactionNo", request.getVnp_TransactionNo());
        vnpParamsMap.put("vnp_TransactionStatus", request.getVnp_TransactionStatus());
        vnpParamsMap.put("vnp_TxnRef", request.getVnp_TxnRef());

        // Build hash data (without SecureHash)
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);

        // Validate secure hash
        if (!vnpSecureHash.equals(request.getVnp_SecureHash())) {
            log.error("VNPay secure hash validation failed. Expected: {}, Got: {}", vnpSecureHash, request.getVnp_SecureHash());
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        // Check payment status
        boolean isSuccess = "00".equals(request.getVnp_ResponseCode()) && 
                           "00".equals(request.getVnp_TransactionStatus());

        if (!isSuccess) {
            log.warn("VNPay payment failed. ResponseCode: {}, TransactionStatus: {}", 
                    request.getVnp_ResponseCode(), request.getVnp_TransactionStatus());
            throw new AppException(ErrorCode.PAYMENT_FAILED);
        }

        // Find or create cost record
        Cost cost = costRepository.findById(request.getCostId()).orElse(null);
        
        if (cost == null) {
            log.warn("Cost id: {} không tồn tại, thử tạo từ treatment phase...", request.getCostId());
            
            var treatmentPhase = treatmentPhasesRepository.findById(request.getCostId()).orElse(null);
            if (treatmentPhase != null && treatmentPhase.getTreatmentPlans() != null) {
                cost = Cost.builder()
                        .id(treatmentPhase.getId())
                        .title("Tiến trình điều trị: " + treatmentPhase.getPhaseNumber() + " - " + 
                               (treatmentPhase.getDescription() != null ? treatmentPhase.getDescription() : ""))
                        .status("wait")
                        .totalCost(treatmentPhase.getCost())
                        .type("phase_payment")
                        .treatmentPlan(treatmentPhase.getTreatmentPlans())
                        .listDentalServiceEntityOrder(treatmentPhase.getListDentalServiceEntityOrder())
                        .listPrescriptionOrder(treatmentPhase.getListPrescriptionOrder())
                        .patient(treatmentPhase.getTreatmentPlans().getPatient())
                        .build();
                
                costRepository.save(cost);
                log.info("Đã tạo cost record id: {} từ treatment phase id: {}", cost.getId(), request.getCostId());
            } else {
                log.error("Chi phí id: {} không tồn tại và không phải là treatment phase id, cập nhật thanh toán thất bại.", request.getCostId());
                throw new AppException(ErrorCode.COST_NOT_EXISTED);
            }
        }

        // Update payment info
        cost.setPaymentDate(LocalDate.now());
        cost.setPaymentMethod(request.getVnp_BankCode() != null ? "VNPay-" + request.getVnp_BankCode() : "VNPay");
        cost.setVnpTxnRef(request.getVnp_TxnRef());
        cost.setStatus("paid");
        
        costRepository.save(cost);
        log.info("Đã cập nhật thanh toán thành công cho cost id: {}", cost.getId());

        return CostResponse.builder()
                .id(cost.getId())
                .title(cost.getTitle())
                .paymentMethod(cost.getPaymentMethod())
                .status(cost.getStatus())
                .totalCost(cost.getTotalCost())
                .paymentDate(cost.getPaymentDate())
                .vnpTxnRef(cost.getVnpTxnRef())
                .type(cost.getType() != null ? cost.getType() : "phase_payment")
                .treatmentPlanId(cost.getTreatmentPlan() != null ? cost.getTreatmentPlan().getId() : null)
                .listDentalServiceEntityOrder(cost.getListDentalServiceEntityOrder())
                .listPrescriptionOrder(cost.getListPrescriptionOrder())
                .build();
    }
}
