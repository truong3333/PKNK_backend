package com.example.pknk.service.clinic;

import com.example.pknk.domain.dto.request.clinic.CostPaymentUpdateRequest;
import com.example.pknk.domain.dto.response.clinic.CostResponse;
import com.example.pknk.domain.entity.clinic.Cost;
import com.example.pknk.domain.entity.user.User;
import com.example.pknk.exception.AppException;
import com.example.pknk.exception.ErrorCode;
import com.example.pknk.repository.clinic.CostRepository;
import com.example.pknk.repository.clinic.ExaminationRepository;
import com.example.pknk.repository.clinic.TreatmentPhasesRepository;
import com.example.pknk.repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)
@Slf4j
public class CostService {
    CostRepository costRepository;
    UserRepository userRepository;
    TreatmentPhasesRepository treatmentPhasesRepository;
    ExaminationRepository examinationRepository;

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
                .listDentalServiceEntityOrder(cost.getListDentalServiceEntityOrder())
                .listPrescriptionOrder(cost.getListPrescriptionOrder())
                .build();
    }
}
