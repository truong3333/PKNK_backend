package com.example.pknk.service.doctor;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.pknk.domain.dto.request.doctor.CommentRequest;
import com.example.pknk.domain.dto.request.doctor.ExaminationRequest;
import com.example.pknk.domain.dto.request.doctor.ExaminationUpdateRequest;
import com.example.pknk.domain.dto.response.clinic.AppointmentResponse;
import com.example.pknk.domain.dto.response.clinic.ExaminationResponse;
import com.example.pknk.domain.dto.response.clinic.ImageResponse;
import com.example.pknk.domain.dto.response.clinic.TreatmentPhasesResponse;
import com.example.pknk.domain.dto.response.doctor.DoctorSummaryResponse;
import com.example.pknk.domain.entity.clinic.*;
import com.example.pknk.domain.entity.user.Doctor;
import com.example.pknk.domain.entity.user.User;
import com.example.pknk.exception.AppException;
import com.example.pknk.exception.ErrorCode;
import com.example.pknk.repository.clinic.*;
import com.example.pknk.repository.doctor.DoctorRepository;
import com.example.pknk.repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DoctorService {
    UserRepository userRepository;
    DoctorRepository doctorRepository;
    AppointmentRepository appointmentRepository;
    ExaminationRepository examinationRepository;
    TreatmentPhasesRepository treatmentPhasesRepository;
    ImageRepository imageRepository;
    CostRepository costRepository;

    Cloudinary cloudinary;

    @PreAuthorize("hasAnyAuthority('GET_INFO_DOCTOR','ADMIN')")
    public DoctorSummaryResponse getInfoDoctorById(String doctorId){
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> {
            log.info("Bác sĩ id: {} không tồn tại, lấy thông tin thất bại.", doctorId);
            throw new AppException(ErrorCode.DOCTOR_NOT_EXISTED);
        });

        return DoctorSummaryResponse.builder()
                .id(doctorId)
                .fullName(doctor.getUser().getUserDetail().getFullName())
                .specialization(doctor.getSpecialization())
                .phone(doctor.getUser().getUserDetail().getPhone())
                .email(doctor.getUser().getUserDetail().getEmail())
                .address(doctor.getUser().getUserDetail().getAddress())
                .gender(doctor.getUser().getUserDetail().getGender())
                .dob(doctor.getUser().getUserDetail().getDob())
                .licenseNumber(doctor.getLicenseNumber())
                .yearsExperience(doctor.getYearsExperience())
                .build();
    }

    @PreAuthorize("hasAnyAuthority('PICK_DOCTOR','ADMIN')")
    public List<DoctorSummaryResponse> getAllDoctors(){
        List<Doctor> doctors = new ArrayList<>(doctorRepository.findAll());
        return doctors.stream().map(d -> DoctorSummaryResponse.builder()
                .id(d.getId())
                .fullName(d.getUser().getUserDetail().getFullName())
                .specialization(d.getSpecialization())
                .build()
        ).toList();
    }

    @PreAuthorize("hasAnyRole('NURSE','ADMIN')")
    public List<AppointmentResponse> getAppointmentScheduledOfDoctor(String doctorId){
        if(!doctorRepository.existsById(doctorId)){
            log.error("Bác sĩ id: {} không tồn tại, lấy danh sách lịch hẹn thất bại.", doctorId);
            throw new AppException(ErrorCode.DOCTOR_NOT_EXISTED);
        }

        return appointmentRepository.findAllByDoctorIdAndStatus(doctorId, "Scheduled").stream().map(appointment -> AppointmentResponse.builder()
                .id(appointment.getId())
                .dateTime(appointment.getDateTime().toString())
                .status(appointment.getStatus())
                .type(appointment.getType())
                .notes(appointment.getNotes())
                .listDentalServicesEntity(appointment.getListDentalServicesEntity())
                .doctorId(doctorId)
                .patientId(appointment.getPatient().getId())
                .notification(appointment.getNotification())
                .build()
        ).toList();
    }

    @PreAuthorize("hasAnyRole('ADMIN','NURSE')")
    public List<AppointmentResponse> getAllAppointmentOfDoctor(String doctorId){
        if(!doctorRepository.existsById(doctorId)){
            log.error("Bác sĩ id: {} không tồn tại, lấy danh sách lịch hẹn thất bại.", doctorId);
            throw new AppException(ErrorCode.DOCTOR_NOT_EXISTED);
        }

        // Loại bỏ các lịch hẹn đã bị patient hủy (status = "Cancel")
        return appointmentRepository.findAllByDoctorIdAndStatusNot(doctorId, "Cancel").stream().map(appointment -> AppointmentResponse.builder()
                .id(appointment.getId())
                .dateTime(appointment.getDateTime().toString())
                .status(appointment.getStatus())
                .type(appointment.getType())
                .notes(appointment.getNotes())
                .listDentalServicesEntity(appointment.getListDentalServicesEntity())
                .doctorId(appointment.getDoctor() != null ? appointment.getDoctor().getId() : doctorId)
                .patientId(appointment.getPatient().getId())
                .notification(appointment.getNotification())
                .build()
        ).toList();
    }

    @PreAuthorize("hasAnyRole('DOCTOR','DOCTORLV2')")
    /**
     * Get all appointments (including cancelled) for a specific doctor
     * @param doctorId - Doctor ID
     * @returns List of all appointments (scheduled, done, cancelled)
     */
    public List<AppointmentResponse> getAllAppointmentsIncludingCancelledOfDoctor(String doctorId){
        if(!doctorRepository.existsById(doctorId)){
            log.error("Bác sĩ id: {} không tồn tại, lấy danh sách lịch hẹn thất bại.", doctorId);
            throw new AppException(ErrorCode.DOCTOR_NOT_EXISTED);
        }

        return appointmentRepository.findAllByDoctorId(doctorId).stream().map(appointment -> AppointmentResponse.builder()
                .id(appointment.getId())
                .dateTime(appointment.getDateTime().toString())
                .status(appointment.getStatus())
                .type(appointment.getType())
                .notes(appointment.getNotes())
                .listDentalServicesEntity(appointment.getListDentalServicesEntity())
                .doctorId(doctorId)
                .patientId(appointment.getPatient().getId())
                .notification(appointment.getNotification())
                .build()
        ).toList();
    }

    @PreAuthorize("hasAnyRole('DOCTOR','DOCTORLV2')")
    public List<AppointmentResponse> getAppointmentScheduledOfMyDoctor(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            log.error("Username: {} không tồn tại, Lấy danh sách lịch hẹn thất bại.", username);
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        });

        return appointmentRepository.findAllByDoctorIdAndStatus(user.getDoctor().getId(), "Scheduled").stream().map(appointment -> AppointmentResponse.builder()
                .id(appointment.getId())
                .dateTime(appointment.getDateTime().toString())
                .status(appointment.getStatus())
                .type(appointment.getType())
                .notes(appointment.getNotes())
                .listDentalServicesEntity(appointment.getListDentalServicesEntity())
                .doctorId(user.getDoctor().getId())
                .patientId(appointment.getPatient().getId())
                .notification(appointment.getNotification())
                .build()
        ).toList();
    }

    @PreAuthorize("hasAnyRole('DOCTOR','DOCTORLV2')")
    public List<AppointmentResponse> getAllAppointmentOfMyDoctor(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            log.error("Username: {} không tồn tại, Lấy danh sách lịch hẹn thất bại.", username);
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        });

        // Loại bỏ các lịch hẹn đã bị patient hủy (status = "Cancel")
        return appointmentRepository.findAllByDoctorIdAndStatusNot(user.getDoctor().getId(), "Cancel").stream().map(appointment -> AppointmentResponse.builder()
                .id(appointment.getId())
                .dateTime(appointment.getDateTime().toString())
                .status(appointment.getStatus())
                .type(appointment.getType())
                .notes(appointment.getNotes())
                .listDentalServicesEntity(appointment.getListDentalServicesEntity())
                .doctorId(user.getDoctor().getId())
                .patientId(appointment.getPatient().getId())
                .notification(appointment.getNotification())
                .build()
        ).toList();
    }

    @PreAuthorize("hasAnyAuthority('CREATE_EXAMINATION','ADMIN')")
    public ExaminationResponse createExamination(String appointmentId, ExaminationRequest request) throws IOException {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> {
            log.error("Lịch hẹn id: {} không tồn tại, thêm kết quả khám thất bại.", appointmentId);
            throw new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED);
        });

        appointment.setStatus("Done");

        Examination examination = Examination.builder()
                .symptoms(request.getSymptoms())
                .diagnosis(request.getDiagnosis())
                .notes(request.getNotes())
                .treatment(request.getTreatment())
                .listDentalServicesEntityOrder(request.getListDentalServicesEntityOrder())
                .listPrescriptionOrder(request.getListPrescriptionOrder())
                .totalCost(request.getTotalCost())
                .examined_at(appointment.getDoctor().getUser().getUserDetail().getFullName())
                .build();

        if (request.getListImageXray() != null && !request.getListImageXray().isEmpty()) {
            for (MultipartFile file : request.getListImageXray()) {
                if (file.isEmpty()) continue;

                Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

                Image image = Image.builder()
                        .publicId((String) result.get("public_id"))
                        .url((String) result.get("secure_url"))
                        .type("examinationXray")
                        .examination(examination)
                        .build();

                examination.getListImage().add(image);
            }
        }

        if (request.getListImageFace() != null && !request.getListImageFace().isEmpty()) {
            for (MultipartFile file : request.getListImageFace()) {
                if (file.isEmpty()) continue;

                Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

                Image image = Image.builder()
                        .publicId((String) result.get("public_id"))
                        .url((String) result.get("secure_url"))
                        .type("examinationFace")
                        .examination(examination)
                        .build();

                examination.getListImage().add(image);
            }
        }

        if (request.getListImageTeeth() != null && !request.getListImageTeeth().isEmpty()) {
            for (MultipartFile file : request.getListImageTeeth()) {
                if (file.isEmpty()) continue;

                Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

                Image image = Image.builder()
                        .publicId((String) result.get("public_id"))
                        .url((String) result.get("secure_url"))
                        .type("examinationTeeth")
                        .examination(examination)
                        .build();

                examination.getListImage().add(image);
            }
        }

        Cost cost = Cost.builder()
                .id(examination.getId())
                .title("Khám theo lịch hẹn: " + appointment.getDateTime())
                .status("wait")
                .totalCost(request.getTotalCost())
                .listDentalServiceEntityOrder(request.getListDentalServicesEntityOrder())
                .listPrescriptionOrder(request.getListPrescriptionOrder())
                .patient(appointment.getPatient())
                .build();

        examination.setAppointment(appointment);
        appointment.setExamination(examination);


        examinationRepository.save(examination);
        costRepository.save(cost);
        log.info("Kết quả khám của lịch hẹn id: {} được thêm thành công.", appointmentId);

        // Lấy tên bệnh nhân từ appointment
        String patientName = appointment.getPatient() != null && appointment.getPatient().getUser() != null 
                && appointment.getPatient().getUser().getUserDetail() != null
                ? appointment.getPatient().getUser().getUserDetail().getFullName()
                : null;

        return ExaminationResponse.builder()
                .id(examination.getId())
                .symptoms(request.getSymptoms())
                .diagnosis(request.getDiagnosis())
                .notes(request.getNotes())
                .treatment(request.getTreatment())
                .examined_at(examination.getAppointment().getDoctor().getUser().getUserDetail().getFullName())
                .patientName(patientName)
                .listDentalServicesEntityOrder(request.getListDentalServicesEntityOrder())
                .listPrescriptionOrder(request.getListPrescriptionOrder())
                .totalCost(request.getTotalCost())
                .listImage(examination.getListImage().stream().map(image -> ImageResponse.builder()
                        .publicId(image.getPublicId())
                        .type(image.getType())
                        .url(image.getUrl())
                        .build()
                ).toList())
                .createAt(examination.getAppointment().getDateTime().toLocalDate())
                .listComment(examination.getListComment())
                .build();
    }

    @PreAuthorize("hasAnyAuthority('UPDATE_EXAMINATION','ADMIN')")
    @Transactional
    public ExaminationResponse updateExamination(String examinationId, ExaminationUpdateRequest request) throws IOException {
        Examination examination = examinationRepository.findById(examinationId).orElseThrow(() -> {
            log.error("Kết quả khám id: {} không tồn tại, cập nhật kết quả khám thất bại.", examinationId);
            throw new AppException(ErrorCode.EXAMINATION_NOT_EXISTED);
        });

        Cost cost = costRepository.findById(examinationId).orElseThrow(() -> {
            log.error("Hoá đơn của kết quả khám id: {} không tồn tại, cập nhật kết quả khám thất bại.", examinationId);
            throw new AppException(ErrorCode.COST_NOT_EXISTED);
        });

        examination.setSymptoms(request.getSymptoms());
        examination.setDiagnosis(request.getDiagnosis());
        examination.setNotes(request.getNotes());
        examination.setTreatment(request.getTreatment());
        examination.setListDentalServicesEntityOrder(request.getListDentalServicesEntityOrder());
        examination.setListPrescriptionOrder(request.getListPrescriptionOrder());
        examination.setTotalCost(request.getTotalCost());

        if (request.getListDeleteImageByPublicId() != null && !request.getListDeleteImageByPublicId().isEmpty()) {
            for (String publicId : request.getListDeleteImageByPublicId()) {
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
                imageRepository.deleteByPublicId(publicId);

                examination.getListImage().removeIf(img -> img.getPublicId().equals(publicId));
            }
        }

        if (request.getListImageXray() != null && !request.getListImageXray().isEmpty()) {
            for (MultipartFile file : request.getListImageXray()) {
                if (file.isEmpty()) continue;

                Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

                Image image = Image.builder()
                        .publicId((String) result.get("public_id"))
                        .url((String) result.get("secure_url"))
                        .type("examinationXray")
                        .examination(examination)
                        .build();

                examination.getListImage().add(image);
            }
        }

        if (request.getListImageFace() != null && !request.getListImageFace().isEmpty()) {
            for (MultipartFile file : request.getListImageFace()) {
                if (file.isEmpty()) continue;

                Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

                Image image = Image.builder()
                        .publicId((String) result.get("public_id"))
                        .url((String) result.get("secure_url"))
                        .type("examinationFace")
                        .examination(examination)
                        .build();

                examination.getListImage().add(image);
            }
        }

        if (request.getListImageTeeth() != null && !request.getListImageTeeth().isEmpty()) {
            for (MultipartFile file : request.getListImageTeeth()) {
                if (file.isEmpty()) continue;

                Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

                Image image = Image.builder()
                        .publicId((String) result.get("public_id"))
                        .url((String) result.get("secure_url"))
                        .type("examinationTeeth")
                        .examination(examination)
                        .build();

                examination.getListImage().add(image);
            }
        }

        cost.setListDentalServiceEntityOrder(request.getListDentalServicesEntityOrder());
        cost.setListPrescriptionOrder(request.getListPrescriptionOrder());
        cost.setTotalCost(request.getTotalCost());

        examinationRepository.save(examination);
        costRepository.save(cost);
        log.info("Kết quả khám id: {} được cập nhật thành công.", examinationId);

        // Lấy tên bệnh nhân từ appointment
        String patientName = examination.getAppointment().getPatient() != null 
                && examination.getAppointment().getPatient().getUser() != null 
                && examination.getAppointment().getPatient().getUser().getUserDetail() != null
                ? examination.getAppointment().getPatient().getUser().getUserDetail().getFullName()
                : null;

        return ExaminationResponse.builder()
                .id(examination.getId())
                .symptoms(request.getSymptoms())
                .diagnosis(request.getDiagnosis())
                .notes(request.getNotes())
                .treatment(request.getTreatment())
                .examined_at(examination.getAppointment().getDoctor().getUser().getUserDetail().getFullName())
                .patientName(patientName)
                .listDentalServicesEntityOrder(request.getListDentalServicesEntityOrder())
                .listPrescriptionOrder(request.getListPrescriptionOrder())
                .totalCost(request.getTotalCost())
                .listImage(examination.getListImage().stream().map(image -> ImageResponse.builder()
                        .publicId(image.getPublicId())
                        .type(image.getType())
                        .url(image.getUrl())
                        .build()
                ).toList())
                .createAt(examination.getAppointment().getDateTime().toLocalDate())
                .listComment(examination.getListComment())
                .build();
    }

    @PreAuthorize("hasAnyAuthority('GET_EXAMINATION_DETAIL','ADMIN')")
    public ExaminationResponse getExaminationByAppointmentId(String appointmentId){
        // Verify appointment exists
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> {
            log.error("Lịch hẹn id: {} không tồn tại, xem kết quả khám thất bại.", appointmentId);
            throw new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED);
        });

        // Query examination directly by appointment_id to avoid lazy loading issues
        Examination examination = examinationRepository.findByAppointmentId(appointmentId).orElseThrow(() -> {
            log.error("Kết quả khám cho lịch hẹn id: {} không tồn tại, xem kết quả khám thất bại.", appointmentId);
            throw new AppException(ErrorCode.EXAMINATION_NOT_EXISTED);
        });

        // Lấy tên bệnh nhân từ appointment
        String patientName = appointment.getPatient() != null && appointment.getPatient().getUser() != null 
                && appointment.getPatient().getUser().getUserDetail() != null
                ? appointment.getPatient().getUser().getUserDetail().getFullName()
                : null;

        return ExaminationResponse.builder()
                .id(examination.getId())
                .symptoms(examination.getSymptoms())
                .diagnosis(examination.getDiagnosis())
                .notes(examination.getNotes())
                .treatment(examination.getTreatment())
                .examined_at(appointment.getDoctor().getUser().getUserDetail().getFullName())
                    .patientName(patientName)
                .listDentalServicesEntityOrder(examination.getListDentalServicesEntityOrder())
                .listPrescriptionOrder(examination.getListPrescriptionOrder())
                .totalCost(examination.getTotalCost())
                .listImage(examination.getListImage().stream().map(image -> ImageResponse.builder()
                        .publicId(image.getPublicId())
                        .type(image.getType())
                        .url(image.getUrl())
                        .build()
                ).toList())
                .createAt(appointment.getDateTime().toLocalDate())
                .listComment(examination.getListComment())
                .build();
    }

    @PreAuthorize("hasAnyRole('DOCTOR','DOCTORLV2')")
    public List<ExaminationResponse> getMyExamination() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            log.error("Username: {} không tồn tại, Lấy danh sách kết quả thất bại.", username);
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        });

        List<Appointment> listAppointment = new ArrayList<>(appointmentRepository.findAllByDoctorIdAndStatusNot(user.getDoctor().getId(), "Cancel"));

        return listAppointment.stream()
                .filter(appointment -> appointment.getExamination() != null)
                .map(appointment -> {
                    Examination examination = appointment.getExamination();
                    // Lấy tên bệnh nhân từ appointment
                    String patientName = appointment.getPatient() != null && appointment.getPatient().getUser() != null 
                            && appointment.getPatient().getUser().getUserDetail() != null
                            ? appointment.getPatient().getUser().getUserDetail().getFullName()
                            : null;
                    return ExaminationResponse.builder()
                            .id(examination.getId())
                            .symptoms(examination.getSymptoms())
                            .diagnosis(examination.getDiagnosis())
                            .notes(examination.getNotes())
                            .treatment(examination.getTreatment())
                            .examined_at(appointment.getDoctor().getUser().getUserDetail().getFullName())
                            .patientName(patientName)
                            .listDentalServicesEntityOrder(examination.getListDentalServicesEntityOrder())
                            .listPrescriptionOrder(examination.getListPrescriptionOrder())
                            .totalCost(examination.getTotalCost())
                            .listImage(examination.getListImage().stream().map(image -> ImageResponse.builder()
                                    .publicId(image.getPublicId())
                                    .type(image.getType())
                                    .url(image.getUrl())
                                    .build()
                            ).toList())
                            .createAt(appointment.getDateTime().toLocalDate())
                            .listComment(examination.getListComment())
                            .build();
                }).toList();
    }

    @PreAuthorize("hasAnyAuthority('GET_EXAMINATION_DETAIL','ADMIN')")
    public ExaminationResponse getExaminationDetailById(String examinationId){
        Examination examination = examinationRepository.findById(examinationId).orElseThrow(() -> {
            log.error("Kết quả khám id: {} không tồn tại, xem chi tiết kết quả khám thất bại.", examinationId);
            throw new AppException(ErrorCode.EXAMINATION_NOT_EXISTED);
        });

        // Lấy tên bệnh nhân từ appointment
        String patientName = examination.getAppointment().getPatient() != null 
                && examination.getAppointment().getPatient().getUser() != null 
                && examination.getAppointment().getPatient().getUser().getUserDetail() != null
                ? examination.getAppointment().getPatient().getUser().getUserDetail().getFullName()
                : null;

        return ExaminationResponse.builder()
                    .id(examination.getId())
                    .symptoms(examination.getSymptoms())
                    .diagnosis(examination.getDiagnosis())
                    .notes(examination.getNotes())
                    .treatment(examination.getTreatment())
                    .examined_at(examination.getAppointment().getDoctor().getUser().getUserDetail().getFullName())
                    .patientName(patientName)
                    .listDentalServicesEntityOrder(examination.getListDentalServicesEntityOrder())
                    .listPrescriptionOrder(examination.getListPrescriptionOrder())
                    .totalCost(examination.getTotalCost())
                    .listImage(examination.getListImage().stream().map(image -> ImageResponse.builder()
                            .publicId(image.getPublicId())
                            .type(image.getType())
                            .url(image.getUrl())
                            .build()
                    ).toList())
                    .createAt(examination.getAppointment().getDateTime().toLocalDate())
                    .listComment(examination.getListComment())
                    .build();
    }


    // DOCTOR LV2
    @PreAuthorize("hasRole('DOCTORLV2')")
    public ExaminationResponse addCommentExaminationByDoctorLV2(String examinationId, CommentRequest request){
        Examination examination = examinationRepository.findById(examinationId).orElseThrow(() -> {
            log.error("Kết quả khám id: {} không tồn tại, thêm nhận xét thất bại.", examinationId);
            throw new AppException(ErrorCode.EXAMINATION_NOT_EXISTED);
        });

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            log.error("Username: {} không tồn tại, thêm nhận xét thất bại.", username);
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        });

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        String now = LocalDateTime.now().format(formatter);

        String comment = "[" + user.getDoctor().getId() + "]" + "[" + now + "]" + user.getUserDetail().getFullName() + ": " + request.getComment()  ;

        examination.getListComment().add(comment);
        examinationRepository.save(examination);
        log.info("Bác sĩ id: {} thêm nhận xét cho kết quả khám id: {} thành công.", user.getDoctor().getId(), examinationId);

        // Lấy tên bệnh nhân từ appointment
        String patientName = examination.getAppointment().getPatient() != null 
                && examination.getAppointment().getPatient().getUser() != null 
                && examination.getAppointment().getPatient().getUser().getUserDetail() != null
                ? examination.getAppointment().getPatient().getUser().getUserDetail().getFullName()
                : null;

        return ExaminationResponse.builder()
                .id(examination.getId())
                .symptoms(examination.getSymptoms())
                .diagnosis(examination.getDiagnosis())
                .notes(examination.getNotes())
                .treatment(examination.getTreatment())
                .examined_at(examination.getAppointment().getDoctor().getUser().getUserDetail().getFullName())
                .patientName(patientName)
                .listDentalServicesEntityOrder(examination.getListDentalServicesEntityOrder())
                .listPrescriptionOrder(examination.getListPrescriptionOrder())
                .totalCost(examination.getTotalCost())
                .listImage(examination.getListImage().stream().map(image -> ImageResponse.builder()
                        .publicId(image.getPublicId())
                        .type(image.getType())
                        .url(image.getUrl())
                        .build()
                ).toList())
                .createAt(examination.getAppointment().getDateTime().toLocalDate())
                .listComment(examination.getListComment())
                .build();
    }

    @PreAuthorize("hasRole('DOCTORLV2')")
    public TreatmentPhasesResponse addCommentTreatmentPhasesByDoctorLV2(String treatmentPhasesId, CommentRequest request){
        TreatmentPhases treatmentPhases = treatmentPhasesRepository.findById(treatmentPhasesId).orElseThrow(() -> {
            log.error("Tiến trình điều trị id: {} không tồn tại, thêm nhận xét thất bại.", treatmentPhasesId);
            throw new AppException(ErrorCode.EXAMINATION_NOT_EXISTED);
        });

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            log.error("Username: {} không tồn tại, thêm nhận xét thất bại.", username);
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        });

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        String now = LocalDateTime.now().format(formatter);

        String comment = "[" + user.getDoctor().getId() + "]" + "[" + now + "]" + user.getUserDetail().getFullName() + ": " + request.getComment()  ;

        treatmentPhases.getListComment().add(comment);
        treatmentPhasesRepository.save(treatmentPhases);
        log.info("Bác sĩ id: {} thêm nhận xét cho tiến trình điều trị id: {} thành công.", user.getDoctor().getId(), treatmentPhasesId);

        return TreatmentPhasesResponse.builder()
                .id(treatmentPhases.getId())
                .phaseNumber(treatmentPhases.getPhaseNumber())
                .description(treatmentPhases.getDescription())
                .listDentalServicesEntityOrder(treatmentPhases.getListDentalServiceEntityOrder())
                .listPrescriptionOrder(treatmentPhases.getListPrescriptionOrder())
                .cost(treatmentPhases.getCost())
                .status(treatmentPhases.getStatus())
                .listComment(treatmentPhases.getListComment())
                .startDate(treatmentPhases.getStartDate())
                .endDate(treatmentPhases.getEndDate())
                .nextAppointment(treatmentPhases.getNextAppointment())
                .listImage(treatmentPhases.getListImage().stream().map(image -> ImageResponse.builder()
                        .publicId(image.getPublicId())
                        .type(image.getType())
                        .url(image.getUrl())
                        .build()
                ).toList())
                .build();
    }
}
