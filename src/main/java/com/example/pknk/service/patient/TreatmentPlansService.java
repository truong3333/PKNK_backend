package com.example.pknk.service.patient;

import com.example.pknk.domain.dto.request.doctor.TreatmentPlansRequest;
import com.example.pknk.domain.dto.request.doctor.TreatmentPlansUpdateRequest;
import com.example.pknk.domain.dto.response.clinic.TreatmentPlansResponse;
import com.example.pknk.domain.entity.clinic.Cost;
import com.example.pknk.domain.entity.clinic.Examination;
import com.example.pknk.domain.entity.clinic.TreatmentPhases;
import com.example.pknk.domain.entity.clinic.TreatmentPlans;
import com.example.pknk.domain.entity.user.Doctor;
import com.example.pknk.domain.entity.user.Nurse;
import com.example.pknk.domain.entity.user.Patient;
import com.example.pknk.domain.entity.user.User;
import com.example.pknk.exception.AppException;
import com.example.pknk.exception.ErrorCode;
import com.example.pknk.repository.clinic.CostRepository;
import com.example.pknk.repository.clinic.ExaminationRepository;
import com.example.pknk.repository.clinic.TreatmentPlansRepository;
import com.example.pknk.repository.doctor.DoctorRepository;
import com.example.pknk.repository.nurse.NurseRepository;
import com.example.pknk.repository.patient.PatientRepository;
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
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class TreatmentPlansService {
    UserRepository userRepository;
    PatientRepository patientRepository;
    NurseRepository nurseRepository;
    DoctorRepository doctorRepository;
    TreatmentPlansRepository treatmentPlansRepository;
    ExaminationRepository examinationRepository;
    CostRepository costRepository;

    @PreAuthorize("hasAnyAuthority('CREATE_TREATMENT_PLANS','ADMIN')")
    public TreatmentPlansResponse createTreatmentPlans(TreatmentPlansRequest request){
        log.info("Creating treatment plan with request: title={}, examinationId={}, nurseId={}, doctorId={}", 
                request.getTitle(), request.getExaminationId(), request.getNurseId(), request.getDoctorId());
        
        // Use findByIdWithAppointment to avoid lazy loading issues
        Examination examination = examinationRepository.findByIdWithAppointment(request.getExaminationId()).orElseThrow(() -> {
            log.error("Kết quả khám id: {} không tồn tại, xem chi tiết kết quả khám thất bại.", request.getExaminationId());
            throw new AppException(ErrorCode.EXAMINATION_NOT_EXISTED);
        });

        if (examination.getAppointment() == null) {
            log.error("Kết quả khám id: {} không có lịch hẹn liên kết, thêm phác đồ điều trị thất bại.", request.getExaminationId());
            throw new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED);
        }

        // If doctorId is provided in request (for DOCTORLV2 with PICK_DOCTOR permission), use it
        // Otherwise, use the doctor from the examination's appointment
        Doctor doctor;
        if (request.getDoctorId() != null && !request.getDoctorId().trim().isEmpty()) {
            doctor = doctorRepository.findById(request.getDoctorId()).orElseThrow(() -> {
                log.error("Bác sĩ id: {} không tồn tại, thêm phác đồ điều trị thất bại.", request.getDoctorId());
                throw new AppException(ErrorCode.DOCTOR_NOT_EXISTED);
            });
            log.info("Sử dụng bác sĩ id: {} từ request (DOCTORLV2 với PICK_DOCTOR permission).", request.getDoctorId());
        } else {
            doctor = doctorRepository.findById(examination.getAppointment().getDoctor().getId()).orElseThrow(() -> {
                log.error("Bác sĩ id: {} không tồn tại, thêm phác đồ điều trị thất bại.", examination.getAppointment().getDoctor().getId());
                throw new AppException(ErrorCode.DOCTOR_NOT_EXISTED);
            });
        }

        Patient patient = patientRepository.findById(examination.getAppointment().getPatient().getId()).orElseThrow(() -> {
            log.error("Bệnh nhân id: {} không tồn tại, thêm phác đồ điều trị thất bại.", examination.getAppointment().getPatient().getId());
            throw new AppException(ErrorCode.PATIENT_NOT_EXISTED);
        });

        // nurseId is optional - only fetch nurse if nurseId is provided
        Nurse nurse = null;
        if (request.getNurseId() != null && !request.getNurseId().trim().isEmpty()) {
            nurse = nurseRepository.findById(request.getNurseId()).orElseThrow(() -> {
                log.error("Y tá id: {} không tồn tại, thêm phác đồ điều trị thất bại.", request.getNurseId());
                throw new AppException(ErrorCode.NURSE_NOT_EXISTED);
            });
        }

        TreatmentPlans treatmentPlans = TreatmentPlans.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .duration(request.getDuration())
                .notes(request.getNotes())
                .totalCost(0)
                .status("Inprogress")
                .patient(patient)
                .doctor(doctor)
                .nurse(nurse)
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
                .status(treatmentPlans.getStatus())
                .doctorId(doctor.getId())
                .doctorFullname(doctor.getUser().getUserDetail().getFullName())
                .nurseId(nurse != null ? nurse.getId() : null) // Handle null nurse
                .nurseFullname(nurse != null ? nurse.getUser().getUserDetail().getFullName() : null) // Handle null nurse
                .patientId(patient.getId())
                .patientName(patient.getUser().getUserDetail().getFullName())
                .createAt(LocalDate.now())
                .build();
    }

    @PreAuthorize("hasAnyAuthority('UPDATE_TREATMENT_PLANS','ADMIN')")
    public TreatmentPlansResponse updateTreatmentPlans(String treatmentPlansId, TreatmentPlansUpdateRequest request){
        TreatmentPlans treatmentPlans = treatmentPlansRepository.findById(treatmentPlansId).orElseThrow(() -> {
            log.error("Phác đồ điều trị id: {} không tồn tại, cập nhật thất bại.", treatmentPlansId);
            throw new AppException(ErrorCode.TREATMENTPLANS_NOT_EXISTED);
        });

        Nurse nurse = nurseRepository.findById(request.getNurseId()).orElseThrow(() -> {
            log.error("Y tá id: {} không tồn tại, cập nhật phác đồ điều trị thất bại.", request.getNurseId());
            throw new AppException(ErrorCode.NURSE_NOT_EXISTED);
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
        treatmentPlans.setNurse(nurse);

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
                .doctorId(treatmentPlans.getDoctor().getId())
                .doctorFullname(treatmentPlans.getDoctor().getUser().getUserDetail().getFullName())
                .nurseId(nurse.getId())
                .nurseFullname(nurse.getUser().getUserDetail().getFullName())
                .patientId(treatmentPlans.getPatient().getId())
                .patientName(treatmentPlans.getPatient().getUser().getUserDetail().getFullName())
                .createAt(treatmentPlans.getCreateAt())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<TreatmentPlansResponse> getAllTreatmentPlans(){
        return treatmentPlansRepository.findAll().stream().map(treatmentPlans -> TreatmentPlansResponse.builder()
                        .id(treatmentPlans.getId())
                        .title(treatmentPlans.getTitle())
                        .description(treatmentPlans.getDescription())
                        .duration(treatmentPlans.getDuration())
                        .notes(treatmentPlans.getNotes())
                        .totalCost(treatmentPlans.getTotalCost())
                        .status(treatmentPlans.getStatus())
                        .doctorId(treatmentPlans.getDoctor().getId())
                        .doctorFullname(treatmentPlans.getDoctor().getUser().getUserDetail().getFullName())
                        .nurseId(treatmentPlans.getNurse() != null ? treatmentPlans.getNurse().getId() : null)
                        .nurseFullname(treatmentPlans.getNurse() != null ? treatmentPlans.getNurse().getUser().getUserDetail().getFullName() : null)
                        .patientId(treatmentPlans.getPatient().getId())
                        .patientName(treatmentPlans.getPatient().getUser().getUserDetail().getFullName())
                        .createAt(treatmentPlans.getCreateAt())
                        .build()
                ).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
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
                .doctorId(treatmentPlans.getDoctor().getId())
                .doctorFullname(treatmentPlans.getDoctor().getUser().getUserDetail().getFullName())
                .nurseId(treatmentPlans.getNurse() != null ? treatmentPlans.getNurse().getId() : null)
                .nurseFullname(treatmentPlans.getNurse() != null ? treatmentPlans.getNurse().getUser().getUserDetail().getFullName() : null)
                .patientId(treatmentPlans.getPatient().getId())
                .patientName(treatmentPlans.getPatient().getUser().getUserDetail().getFullName())
                .createAt(treatmentPlans.getCreateAt())
                .build()).toList();
    }

    @PreAuthorize("hasAnyAuthority('PICK_DOCTOR','ADMIN')")
    public List<TreatmentPlansResponse> getTreatmentPlansByDoctorId(String doctorId) {
        if (!doctorRepository.existsById(doctorId)) {
            log.error("Bác sĩ id: {} không tồn tại, lấy danh sách phác đồ điều trị thất bại.", doctorId);
            throw new AppException(ErrorCode.DOCTOR_NOT_EXISTED);
        }

        List<TreatmentPlans> listTreatmentPlans = new ArrayList<>(treatmentPlansRepository.findAllByDoctorId(doctorId));

        return listTreatmentPlans.stream().map(treatmentPlans -> TreatmentPlansResponse.builder()
                .id(treatmentPlans.getId())
                .title(treatmentPlans.getTitle())
                .description(treatmentPlans.getDescription())
                .duration(treatmentPlans.getDuration())
                .notes(treatmentPlans.getNotes())
                .totalCost(treatmentPlans.getTotalCost())
                .status(treatmentPlans.getStatus())
                .doctorId(treatmentPlans.getDoctor().getId())
                .doctorFullname(treatmentPlans.getDoctor().getUser().getUserDetail().getFullName())
                .nurseId(treatmentPlans.getNurse() != null ? treatmentPlans.getNurse().getId() : null)
                .nurseFullname(treatmentPlans.getNurse() != null ? treatmentPlans.getNurse().getUser().getUserDetail().getFullName() : null)
                .patientId(treatmentPlans.getPatient().getId())
                .patientName(treatmentPlans.getPatient().getUser().getUserDetail().getFullName())
                .createAt(treatmentPlans.getCreateAt())
                .build()).toList();
    }

    @PreAuthorize("hasAnyRole('DOCTOR','DOCTORLV2')")
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
                .doctorId(treatmentPlans.getDoctor().getId())
                .doctorFullname(treatmentPlans.getDoctor().getUser().getUserDetail().getFullName())
                .nurseId(treatmentPlans.getNurse() != null ? treatmentPlans.getNurse().getId() : null)
                .nurseFullname(treatmentPlans.getNurse() != null ? treatmentPlans.getNurse().getUser().getUserDetail().getFullName() : null)
                .patientId(treatmentPlans.getPatient().getId())
                .patientName(treatmentPlans.getPatient().getUser().getUserDetail().getFullName())
                .createAt(treatmentPlans.getCreateAt())
                .build()).toList();
    }

    @PreAuthorize("hasRole('NURSE')")
    public List<TreatmentPlansResponse> getMyTreatmentPlansOfNurse(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            log.error("Username: {} không tồn tại, Lấy danh sách phác đồ điều trị thất bại.", username);
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        });

        List<TreatmentPlans> listTreatmentPlans = new ArrayList<>(treatmentPlansRepository.findAllByNurseId(user.getNurse().getId())) ;

        return listTreatmentPlans.stream().map(treatmentPlans -> TreatmentPlansResponse.builder()
                .id(treatmentPlans.getId())
                .title(treatmentPlans.getTitle())
                .description(treatmentPlans.getDescription())
                .duration(treatmentPlans.getDuration())
                .notes(treatmentPlans.getNotes())
                .totalCost(treatmentPlans.getTotalCost())
                .status(treatmentPlans.getStatus())
                .doctorId(treatmentPlans.getDoctor().getId())
                .doctorFullname(treatmentPlans.getDoctor().getUser().getUserDetail().getFullName())
                .nurseId(treatmentPlans.getNurse() != null ? treatmentPlans.getNurse().getId() : null)
                .nurseFullname(treatmentPlans.getNurse() != null ? treatmentPlans.getNurse().getUser().getUserDetail().getFullName() : null)
                .patientId(treatmentPlans.getPatient().getId())
                .patientName(treatmentPlans.getPatient().getUser().getUserDetail().getFullName())
                .createAt(treatmentPlans.getCreateAt())
                .build()).toList();
    }

    @PreAuthorize("hasRole('PATIENT')")
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
                .doctorId(treatmentPlans.getDoctor().getId())
                .doctorFullname(treatmentPlans.getDoctor().getUser().getUserDetail().getFullName())
                .nurseId(treatmentPlans.getNurse() != null ? treatmentPlans.getNurse().getId() : null) // Handle null nurse
                .nurseFullname(treatmentPlans.getNurse() != null ? treatmentPlans.getNurse().getUser().getUserDetail().getFullName() : null) // Handle null nurse
                .patientId(treatmentPlans.getPatient().getId())
                .patientName(treatmentPlans.getPatient().getUser().getUserDetail().getFullName())
                .createAt(treatmentPlans.getCreateAt())
                .build()).toList();
    }
}
