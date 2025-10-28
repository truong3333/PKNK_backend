package com.example.pknk.service.clinic;

import com.example.pknk.domain.dto.request.clinic.PrescriptionRequest;
import com.example.pknk.domain.dto.request.clinic.PrescriptionUpdateRequest;
import com.example.pknk.domain.dto.response.clinic.PrescriptionResponse;
import com.example.pknk.domain.entity.clinic.Prescription;
import com.example.pknk.exception.AppException;
import com.example.pknk.exception.ErrorCode;
import com.example.pknk.repository.clinic.PrescriptionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Struct;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PrescriptionService {
    PrescriptionRepository prescriptionRepository;

    public PrescriptionResponse createPrescription(PrescriptionRequest request){
        if(prescriptionRepository.existsById(request.getName())){
            log.error("Thuốc: {} đã tồn tại, thêm thuốc thất bại.", request.getName());
            throw new AppException(ErrorCode.PRESCRIPTION_EXISTED);
        }

        Prescription prescription = Prescription.builder()
                .name(request.getName())
                .dosage(request.getDosage())
                .frequency(request.getFrequency())
                .duration(request.getDuration())
                .notes(request.getNotes())
                .unitPrice(request.getUnitPrice())
                .build();

        prescriptionRepository.save(prescription);
        log.info("Thuốc: {} được thêm thành công", request.getName());

        return PrescriptionResponse.builder()
                .name(request.getName())
                .dosage(request.getDosage())
                .frequency(request.getFrequency())
                .duration(request.getDuration())
                .notes(request.getNotes())
                .unitPrice(request.getUnitPrice())
                .build();
    }

    public PrescriptionResponse updatePrescription(String prescriptionName, PrescriptionUpdateRequest request){
        Prescription prescription = prescriptionRepository.findById(prescriptionName).orElseThrow(() -> {
            log.error("Thuốc: {} không tồn tại, cập nhật thất bại.", prescriptionName);
            throw new AppException(ErrorCode.PRESCRIPTION_NOT_EXISTED);
        });

        prescription.setDosage(request.getDosage());
        prescription.setFrequency(request.getFrequency());
        prescription.setDuration(request.getDuration());
        prescription.setNotes(request.getNotes());
        prescription.setUnitPrice(request.getUnitPrice());

        prescriptionRepository.save(prescription);
        log.info("Thuốc: {} cập nhật thành công.", prescriptionName);

        return PrescriptionResponse.builder()
                .name(prescriptionName)
                .dosage(request.getDosage())
                .frequency(request.getFrequency())
                .duration(request.getDuration())
                .notes(request.getNotes())
                .unitPrice(request.getUnitPrice())
                .build();
    }

    public List<PrescriptionResponse> getAllPrescription(){
        return prescriptionRepository.findAll().stream().map(prescription -> PrescriptionResponse.builder()
                        .name(prescription.getName())
                        .dosage(prescription.getDosage())
                        .frequency(prescription.getFrequency())
                        .duration(prescription.getDuration())
                        .notes(prescription.getNotes())
                        .unitPrice(prescription.getUnitPrice())
                        .build()
                ).toList();
    }

}
