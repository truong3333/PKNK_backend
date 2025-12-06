package com.example.pknk.service.clinic;

import com.example.pknk.domain.dto.request.clinic.CostPaymentUpdateRequest;
import com.example.pknk.domain.dto.response.clinic.CostResponse;
import com.example.pknk.domain.entity.clinic.Cost;
import com.example.pknk.domain.entity.user.User;
import com.example.pknk.exception.AppException;
import com.example.pknk.exception.ErrorCode;
import com.example.pknk.repository.clinic.CostRepository;
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

    @PreAuthorize("hasRole('PATIENT')")
    public List<CostResponse> getAllMyCost(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            log.error("Username: {} không tồn tại, Lấy danh sách kết quả thất bại.", username);
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        });

        List<Cost> listCost = new ArrayList<>(costRepository.findAllByPatientId(user.getPatient().getId()));

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

    @PreAuthorize("hasAuthority('UPDATE_PAYMENT_COST','ADMIN')")
    public CostResponse updatePaymentCost(String costId, CostPaymentUpdateRequest request){
        Cost cost = costRepository.findById(costId).orElseThrow(() -> {
            log.error("Chi phí id: {} không tồn tại, cập nhật thanh toán thất bại.", costId);
            throw new AppException(ErrorCode.COST_NOT_EXISTED);
        });

        cost.setPaymentDate(LocalDate.now());
        cost.setPaymentMethod(request.getPaymentMethod());
        cost.setVnpTxnRef(request.getVnpTxnRef());
        cost.setStatus(request.getStatus());

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
