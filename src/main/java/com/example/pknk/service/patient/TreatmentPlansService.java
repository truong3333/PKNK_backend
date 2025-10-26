package com.example.pknk.service.patient;

import com.example.pknk.domain.dto.request.doctor.TreatmentPlansRequest;
import com.example.pknk.domain.dto.request.doctor.TreatmentPlansUpdateRequest;
import com.example.pknk.domain.dto.response.clinic.TreatmentPlansResponse;
import com.example.pknk.domain.entity.clinic.Examination;
import com.example.pknk.domain.entity.clinic.TreatmentPhases;
import com.example.pknk.domain.entity.clinic.TreatmentPlans;
import com.example.pknk.domain.entity.user.Doctor;
import com.example.pknk.domain.entity.user.Patient;
import com.example.pknk.domain.entity.user.User;
import com.example.pknk.exception.AppException;
import com.example.pknk.exception.ErrorCode;
import com.example.pknk.repository.clinic.ExaminationRepository;
import com.example.pknk.repository.clinic.TreatmentPlansRepository;
import com.example.pknk.repository.doctor.DoctorRepository;
import com.example.pknk.repository.patient.PatientRepository;
import com.example.pknk.repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class TreatmentPlansService {
    UserRepository userRepository;
    PatientRepository patientRepository;
    DoctorRepository doctorRepository;
    TreatmentPlansRepository treatmentPlansRepository;
    ExaminationRepository examinationRepository;

    public TreatmentPlansResponse createTreatmentPlans(TreatmentPlansRequest request){
        Examination examination = examinationRepository.findById(request.getExaminationId()).orElseThrow(() -> {
            log.error("Kết quả khám id: {} không tồn tại, xem chi tiết kết quả khám thất bại.", request.getExaminationId());
            throw new AppException(ErrorCode.EXAMINATION_NOT_EXISTED);
        });

        Doctor doctor = doctorRepository.findById(examination.getAppointment().getDoctor().getId()).orElseThrow(() -> {
            log.error("Bác sĩ id: {} không tồn tại, thêm phác đồ điều trị thất bại.", examination.getAppointment().getDoctor().getId());
            throw new AppException(ErrorCode.DOCTOR_NOT_EXISTED);
        });

        Patient patient = patientRepository.findById(examination.getAppointment().getPatient().getId()).orElseThrow(() -> {
            log.error("Bệnh nhân id: {} không tồn tại, thêm phác đồ điều trị thất bại.", examination.getAppointment().getPatient().getId());
            throw new AppException(ErrorCode.PATIENT_NOT_EXISTED);
        });


        TreatmentPlans treatmentPlans = TreatmentPlans.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .duration(request.getDuration())
                .notes(request.getNotes())
                .totalCost(0)
                .status("Inprogress")
                .patient(patient)
                .doctor(doctor)
                .createAt(LocalDate.now())
                .build();

        treatmentPlansRepository.save(treatmentPlans);
        log.info("phác đồ điều trị của bệnh nhân id: {} được bác sĩ id: {} thêm thành công.", examination.getAppointment().getPatient().getId(), examination.getAppointment().getDoctor().getId());

        return TreatmentPlansResponse.builder()
                .id(treatmentPlans.getId())
                .title(request.getTitle())
                .description(request.getDescription())
                .duration(request.getDuration())
                .notes(request.getNotes())
                .totalCost(0)
                .status("Inprogress")
                .doctorFullname(doctor.getUser().getUserDetail().getFullName())
                .createAt(LocalDate.now())
                .build();
    }

    public TreatmentPlansResponse updateTreatmentPlans(String treatmentPlansId, TreatmentPlansUpdateRequest request){
        TreatmentPlans treatmentPlans = treatmentPlansRepository.findById(treatmentPlansId).orElseThrow(() -> {
            log.error("Phác đồ điều trị id: {} không tồn tại, cập nhật thất bại.", treatmentPlansId);
            throw new AppException(ErrorCode.TREATMENTPLANS_NOT_EXISTED);
        });

        double cost = 0;
        for(TreatmentPhases phases : treatmentPlans.getListTreatmentPhases()){
            cost += phases.getCost();
        }

        treatmentPlans.setTitle(request.getTitle());
        treatmentPlans.setDescription(request.getDescription());
        treatmentPlans.setDuration(request.getDuration());
        treatmentPlans.setNotes(request.getNotes());
        treatmentPlans.setStatus(request.getStatus());
        treatmentPlans.setTotalCost(cost);

        treatmentPlansRepository.save(treatmentPlans);
        log.info("Phác đồ điều trị id: {} cập nhật thành công.", treatmentPlansId);

        return TreatmentPlansResponse.builder()
                .id(treatmentPlans.getId())
                .title(request.getTitle())
                .description(request.getDescription())
                .duration(request.getDuration())
                .notes(request.getNotes())
                .totalCost(cost)
                .status(request.getStatus())
                .doctorFullname(treatmentPlans.getDoctor().getUser().getUserDetail().getFullName())
                .createAt(treatmentPlans.getCreateAt())
                .build();
    }

    public List<TreatmentPlansResponse> getAllTreatmentPlans(){
        return treatmentPlansRepository.findAll().stream().map(treatmentPlans -> TreatmentPlansResponse.builder()
                        .id(treatmentPlans.getId())
                        .title(treatmentPlans.getTitle())
                        .description(treatmentPlans.getDescription())
                        .duration(treatmentPlans.getDuration())
                        .notes(treatmentPlans.getNotes())
                        .totalCost(treatmentPlans.getTotalCost())
                        .status(treatmentPlans.getStatus())
                        .doctorFullname(treatmentPlans.getDoctor().getUser().getUserDetail().getFullName())
                        .createAt(LocalDate.now())
                        .build()
                ).toList();
    }

    public List<TreatmentPlansResponse> getAllTreatmentPlansByPatientId(String patientId){
        List<TreatmentPlans> listTreatmentPlans = new ArrayList<>(treatmentPlansRepository.findAllByPatientId(patientId)) ;

        return listTreatmentPlans.stream().map(treatmentPlans -> TreatmentPlansResponse.builder()
                .id(treatmentPlans.getId())
                .title(treatmentPlans.getTitle())
                .description(treatmentPlans.getDescription())
                .duration(treatmentPlans.getDuration())
                .notes(treatmentPlans.getNotes())
                .totalCost(treatmentPlans.getTotalCost())
                .status(treatmentPlans.getStatus())
                .doctorFullname(treatmentPlans.getDoctor().getUser().getUserDetail().getFullName())
                .createAt(LocalDate.now())
                .build()).toList();
    }

    public List<TreatmentPlansResponse> getMyTreatmentPlansOfDoctor(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            log.error("Username: {} không tồn tại, Lấy danh sách phác đồ điều trị thất bại.", username);
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        });

        List<TreatmentPlans> listTreatmentPlans = new ArrayList<>(treatmentPlansRepository.findAllByDoctorId(user.getDoctor().getId())) ;

        return listTreatmentPlans.stream().map(treatmentPlans -> TreatmentPlansResponse.builder()
                .id(treatmentPlans.getId())
                .title(treatmentPlans.getTitle())
                .description(treatmentPlans.getDescription())
                .duration(treatmentPlans.getDuration())
                .notes(treatmentPlans.getNotes())
                .totalCost(treatmentPlans.getTotalCost())
                .status(treatmentPlans.getStatus())
                .doctorFullname(treatmentPlans.getDoctor().getUser().getUserDetail().getFullName())
                .createAt(LocalDate.now())
                .build()).toList();
    }

    public List<TreatmentPlansResponse> getMyTreatmentPlansOfPatient(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            log.error("Username: {} không tồn tại, Lấy danh sách phác đồ điều trị thất bại.", username);
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        });

        List<TreatmentPlans> listTreatmentPlans = new ArrayList<>(treatmentPlansRepository.findAllByPatientId(user.getPatient().getId())) ;

        return listTreatmentPlans.stream().map(treatmentPlans -> TreatmentPlansResponse.builder()
                .id(treatmentPlans.getId())
                .title(treatmentPlans.getTitle())
                .description(treatmentPlans.getDescription())
                .duration(treatmentPlans.getDuration())
                .notes(treatmentPlans.getNotes())
                .totalCost(treatmentPlans.getTotalCost())
                .status(treatmentPlans.getStatus())
                .doctorFullname(treatmentPlans.getDoctor().getUser().getUserDetail().getFullName())
                .createAt(LocalDate.now())
                .build()).toList();
    }
}
