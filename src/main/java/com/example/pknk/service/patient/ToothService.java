package com.example.pknk.service.patient;

import com.example.pknk.domain.dto.request.patient.ToothRequest;
import com.example.pknk.domain.dto.request.patient.ToothUpdateRequest;
import com.example.pknk.domain.dto.response.patient.ToothResponse;
import com.example.pknk.domain.entity.clinic.Tooth;
import com.example.pknk.domain.entity.user.Patient;
import com.example.pknk.exception.AppException;
import com.example.pknk.exception.ErrorCode;
import com.example.pknk.repository.patient.PatientRepository;
import com.example.pknk.repository.patient.ToothRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class ToothService {
    ToothRepository toothRepository;
    PatientRepository patientRepository;

    @PreAuthorize("hasAuthority('CREATE_TOOTH_STATUS','ADMIN')")
    public ToothResponse createToothStatus(String patientId, ToothRequest request){
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> {
            log.error("Bệnh nhân id: {} không tồn tại, thêm thông tin răng thất bại.", patientId);
            throw new AppException(ErrorCode.PATIENT_NOT_EXISTED);
        });

        if(toothRepository.existsByPatientIdAndToothNumber(patientId, request.getToothNumber())){
            log.error("Răng số: {} đã tồn tại, thêm thông tin răng thất bại.", request.getToothNumber());
            throw new AppException(ErrorCode.TOOTH_NUMBER_EXISTED);
        }

        Tooth tooth = Tooth.builder()
                .toothNumber(request.getToothNumber())
                .status(request.getStatus())
                .patient(patient)
                .build();

        toothRepository.save(tooth);
        log.info("Bệnh nhân id: {} đã thêm thông tin răng số: {} thành công .", patientId, request.getToothNumber());

        return ToothResponse.builder()
                .id(tooth.getId())
                .toothNumber(request.getToothNumber())
                .status(request.getStatus())
                .build();
    }

    @PreAuthorize("hasAuthority('UPDATE_TOOTH_STATUS','ADMIN')")
    public ToothResponse updateToothStatus(String toothId, ToothUpdateRequest request){
        Tooth tooth = toothRepository.findById(toothId).orElseThrow(() -> {
            log.error("Trạng thái răng id: {} không tồn tại, cập nhật thất bại.", toothId);
            throw new AppException(ErrorCode.TOOTH_NOT_EXISTED);
        });

        tooth.setStatus(request.getStatus());

        toothRepository.save(tooth);
        log.info("Trạng thái răng id: {} cập nhật thành công", toothId);

        return ToothResponse.builder()
                .id(toothId)
                .toothNumber(tooth.getToothNumber())
                .status(request.getStatus())
                .build();
    }

    @PreAuthorize("hasAuthority('GET_TOOTH_STATUS','ADMIN')")
    public List<ToothResponse> getAllToothStatusOfPatient(String patientId){
        List<Tooth> listTooth = new ArrayList<>(toothRepository.findAllByPatientId(patientId));

        return listTooth.stream().map(tooth -> ToothResponse.builder()
                .id(tooth.getId())
                .toothNumber(tooth.getToothNumber())
                .status(tooth.getStatus())
                .build()
        ).toList();
    }
}
