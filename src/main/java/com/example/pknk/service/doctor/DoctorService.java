package com.example.pknk.service.doctor;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.pknk.domain.dto.request.doctor.ExaminationRequest;
import com.example.pknk.domain.dto.request.doctor.ExaminationUpdateRequest;
import com.example.pknk.domain.dto.response.clinic.AppointmentResponse;
import com.example.pknk.domain.dto.response.clinic.ExaminationResponse;
import com.example.pknk.domain.dto.response.clinic.ImageResponse;
import com.example.pknk.domain.entity.clinic.Appointment;
import com.example.pknk.domain.entity.clinic.DentalServicesEntity;
import com.example.pknk.domain.entity.clinic.Examination;
import com.example.pknk.domain.entity.clinic.Image;
import com.example.pknk.domain.entity.user.User;
import com.example.pknk.exception.AppException;
import com.example.pknk.exception.ErrorCode;
import com.example.pknk.repository.clinic.AppointmentRepository;
import com.example.pknk.repository.clinic.ExaminationRepository;
import com.example.pknk.repository.clinic.ImageRepository;
import com.example.pknk.repository.doctor.DoctorRepository;
import com.example.pknk.repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    ImageRepository imageRepository;

    Cloudinary cloudinary;

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
                .doctorFullName(appointment.getDoctor().getUser().getUserDetail().getFullName())
                .doctorSpecialization(appointment.getDoctor().getSpecialization())
                .build()
        ).toList();
    }

    public List<AppointmentResponse> getAllAppointmentOfDoctor(String doctorId){
        if(!doctorRepository.existsById(doctorId)){
            log.error("Bác sĩ id: {} không tồn tại, lấy danh sách lịch hẹn thất bại.", doctorId);
            throw new AppException(ErrorCode.DOCTOR_NOT_EXISTED);
        }

        return appointmentRepository.findAllByDoctorIdAndStatusNot(doctorId, "Cancel").stream().map(appointment -> AppointmentResponse.builder()
                .id(appointment.getId())
                .dateTime(appointment.getDateTime().toString())
                .status(appointment.getStatus())
                .type(appointment.getType())
                .notes(appointment.getNotes())
                .listDentalServicesEntity(appointment.getListDentalServicesEntity())
                .doctorFullName(appointment.getDoctor().getUser().getUserDetail().getFullName())
                .doctorSpecialization(appointment.getDoctor().getSpecialization())
                .build()
        ).toList();
    }

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
                .doctorFullName(appointment.getDoctor().getUser().getUserDetail().getFullName())
                .doctorSpecialization(appointment.getDoctor().getSpecialization())
                .build()
        ).toList();
    }

    public List<AppointmentResponse> getAllAppointmentScheduledOfMyDoctor(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            log.error("Username: {} không tồn tại, Lấy danh sách lịch hẹn thất bại.", username);
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        });

        return appointmentRepository.findAllByDoctorIdAndStatusNot(user.getDoctor().getId(), "Cancel").stream().map(appointment -> AppointmentResponse.builder()
                .id(appointment.getId())
                .dateTime(appointment.getDateTime().toString())
                .status(appointment.getStatus())
                .type(appointment.getType())
                .notes(appointment.getNotes())
                .listDentalServicesEntity(appointment.getListDentalServicesEntity())
                .doctorFullName(appointment.getDoctor().getUser().getUserDetail().getFullName())
                .doctorSpecialization(appointment.getDoctor().getSpecialization())
                .build()
        ).toList();
    }

    public ExaminationResponse createExamination(String appointmentId, ExaminationRequest request) throws IOException {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> {
            log.error("Lịch hẹn id: {} không tồn tại, thêm kết quả khám thất bại.", appointmentId);
            throw new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED);
        });

        double cost = 0;
        for(DentalServicesEntity services : request.getListDentalServicesEntity())
            cost += services.getPrice();

        appointment.setStatus("Done");

        Examination examination = Examination.builder()
                .symptoms(request.getSymptoms())
                .diagnosis(request.getDiagnosis())
                .notes(request.getNotes())
                .treatment(request.getTreatment())
                .listDentalServicesEntity(request.getListDentalServicesEntity())
                .totalCost(cost)
                .examined_at(appointment.getDoctor().getUser().getUserDetail().getFullName())
                .build();

        List<Image> listImage = new ArrayList<>();

        try {
            for(MultipartFile file : request.getListImageFile()) {
                Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type","auto"));

                Image image = Image.builder()
                        .publicId((String) result.get("public_id"))
                        .url((String) result.get("secure_url"))
                        .type("examination")
                        .examination(examination)
                        .build();

                examination.getListImage().add(image);
                listImage.add(image);
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        examination.setAppointment(appointment);
        appointment.setExamination(examination);

        examinationRepository.save(examination);
        log.info("Kết quả khám của lịch hẹn id: {} được thêm thành công.", appointmentId);


        return ExaminationResponse.builder()
                .id(examination.getId())
                .symptoms(request.getSymptoms())
                .diagnosis(request.getDiagnosis())
                .notes(request.getNotes())
                .treatment(request.getTreatment())
                .examined_at(appointment.getDoctor().getUser().getUserDetail().getFullName())
                .totalCost(cost)
                .listDentalServicesEntity(request.getListDentalServicesEntity())
                .listImage(listImage.stream().map(image -> ImageResponse.builder()
                        .publicId(image.getPublicId())
                        .url(image.getUrl())
                        .build()
                ).toList())
                .createAt(appointment.getDateTime().toLocalDate())
                .build();
    }

    @Transactional
    public ExaminationResponse updateExamination(String examinationId, ExaminationUpdateRequest request) throws IOException {
        Examination examination = examinationRepository.findById(examinationId).orElseThrow(() -> {
            log.error("Kết quả khám id: {} không tồn tại, cập nhật kết quả khám thất bại.", examinationId);
            throw new AppException(ErrorCode.EXAMINATION_NOT_EXISTED);
        });

        double cost = 0;
        for(DentalServicesEntity services : request.getListDentalServicesEntity())
            cost += services.getPrice();

        examination.setSymptoms(request.getSymptoms());
        examination.setDiagnosis(request.getDiagnosis());
        examination.setNotes(request.getNotes());
        examination.setTreatment(request.getTreatment());
        examination.setListDentalServicesEntity(request.getListDentalServicesEntity());
        examination.setTotalCost(cost);

        if (request.getListDeleteImageByPublicId() != null && !request.getListDeleteImageByPublicId().isEmpty()) {
            for (String publicId : request.getListDeleteImageByPublicId()) {
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
                imageRepository.deleteByPublicId(publicId);

                examination.getListImage().removeIf(img -> img.getPublicId().equals(publicId));
            }
        }

        if (request.getListImageFile() != null && !request.getListImageFile().isEmpty()) {
            for (MultipartFile file : request.getListImageFile()) {
                if (file.isEmpty()) continue;

                Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

                Image image = Image.builder()
                        .publicId((String) result.get("public_id"))
                        .url((String) result.get("secure_url"))
                        .type("examination")
                        .examination(examination)
                        .build();

                examination.getListImage().add(image);
            }
        }

        examinationRepository.save(examination);
        log.info("Kết quả khám id: {} được cập nhật thành công.", examinationId);

        return ExaminationResponse.builder()
                .id(examination.getId())
                .symptoms(request.getSymptoms())
                .diagnosis(request.getDiagnosis())
                .notes(request.getNotes())
                .treatment(request.getTreatment())
                .examined_at(examination.getAppointment().getDoctor().getUser().getUserDetail().getFullName())
                .listImage(examination.getListImage().stream().map(image -> ImageResponse.builder()
                        .publicId(image.getPublicId())
                        .url(image.getUrl())
                        .build()
                ).toList())
                .createAt(examination.getAppointment().getDateTime().toLocalDate())
                .build();
    }

    public ExaminationResponse getExaminationByAppointmentId(String appointmentId){
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> {
            log.error("Lịch hẹn id: {} không tồn tại, xem kết quả khám thất bại.", appointmentId);
            throw new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED);
        });

        Examination examination = examinationRepository.findById(appointment.getExamination().getId()).orElseThrow(() -> {
            log.error("Kết quả khám id: {} không tồn tại, xem kết quả khám thất bại.", appointment.getExamination().getId());
            throw new AppException(ErrorCode.EXAMINATION_NOT_EXISTED);
        });

        return ExaminationResponse.builder()
                .id(examination.getId())
                .symptoms(examination.getSymptoms())
                .diagnosis(examination.getDiagnosis())
                .notes(examination.getNotes())
                .treatment(examination.getTreatment())
                .examined_at(appointment.getDoctor().getUser().getUserDetail().getFullName())
                .createAt(appointment.getDateTime().toLocalDate())
                .build();
    }

    public List<ExaminationResponse> getMyExamination() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            log.error("Username: {} không tồn tại, Lấy danh sách kết quả thất bại.", username);
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        });

        List<Appointment> listAppointment = new ArrayList<>(appointmentRepository.findAllByDoctorIdAndStatusNot(user.getDoctor().getId(), "Cancel"));

        return listAppointment.stream()
                .filter(appointment -> appointment.getExamination() != null)
                .map(appointment -> ExaminationResponse.builder()
                        .id(appointment.getExamination().getId())
                        .symptoms(appointment.getExamination().getSymptoms())
                        .diagnosis(appointment.getExamination().getDiagnosis())
                        .notes(appointment.getExamination().getNotes())
                        .treatment(appointment.getExamination().getTreatment())
                        .examined_at(appointment.getDoctor().getUser().getUserDetail().getFullName())
                        .createAt(appointment.getDateTime().toLocalDate())
                        .listDentalServicesEntity(appointment.getExamination().getListDentalServicesEntity())
                        .totalCost(appointment.getExamination().getTotalCost())
                        .listImage(appointment.getExamination().getListImage().stream().map(image -> ImageResponse.builder()
                                .publicId(image.getPublicId())
                                .url(image.getUrl())
                                .build()
                        ).toList())
                        .build()
        ).toList();
    }

    public ExaminationResponse getExaminationDetailById(String examinationId){
        Examination examination = examinationRepository.findById(examinationId).orElseThrow(() -> {
            log.error("Kết quả khám id: {} không tồn tại, xem chi tiết kết quả khám thất bại.", examinationId);
            throw new AppException(ErrorCode.EXAMINATION_NOT_EXISTED);
        });

        return ExaminationResponse.builder()
                    .id(examination.getId())
                    .symptoms(examination.getSymptoms())
                    .diagnosis(examination.getDiagnosis())
                    .notes(examination.getNotes())
                    .treatment(examination.getTreatment())
                    .examined_at(examination.getAppointment().getDoctor().getUser().getUserDetail().getFullName())
                    .listDentalServicesEntity(examination.getListDentalServicesEntity())
                    .totalCost(examination.getTotalCost())
                    .listImage(examination.getListImage().stream().map(image -> ImageResponse.builder()
                            .publicId(image.getPublicId())
                            .url(image.getUrl())
                            .build()
                    ).toList())
                    .createAt(examination.getAppointment().getDateTime().toLocalDate())
                    .build();
    }
}
