package com.example.pknk.service.patient;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.pknk.domain.dto.request.doctor.TreatmentPhasesRequest;
import com.example.pknk.domain.dto.request.doctor.TreatmentPhasesUpdateRequest;
import com.example.pknk.domain.dto.response.clinic.ImageResponse;
import com.example.pknk.domain.dto.response.clinic.TreatmentPhasesResponse;
import com.example.pknk.domain.entity.clinic.*;
import com.example.pknk.exception.AppException;
import com.example.pknk.exception.ErrorCode;
import com.example.pknk.repository.clinic.AppointmentRepository;
import com.example.pknk.repository.clinic.ImageRepository;
import com.example.pknk.repository.clinic.TreatmentPhasesRepository;
import com.example.pknk.repository.clinic.TreatmentPlansRepository;
import com.example.pknk.repository.doctor.BookingDateTimeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class TreatmentPhasesService {
    TreatmentPlansRepository treatmentPlansRepository;
    TreatmentPhasesRepository treatmentPhasesRepository;
    BookingDateTimeRepository bookingDateTimeRepository;
    AppointmentRepository appointmentRepository;
    ImageRepository imageRepository;

    Cloudinary cloudinary;

    @PreAuthorize("hasAuthority('CREATE_TREATMENT_PHASES','ADMIN')")
    public TreatmentPhasesResponse createTreatmentPhases(String treatmentPlansId,TreatmentPhasesRequest request) throws IOException {
        TreatmentPlans treatmentPlans = treatmentPlansRepository.findById(treatmentPlansId).orElseThrow(() -> {
            log.error("Phác đồ điều trị id: {} không tồn tại, thêm tiến trình điều trị thất bại.", treatmentPlansId);
            throw new AppException(ErrorCode.TREATMENTPLANS_NOT_EXISTED);
        });

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        TreatmentPhases treatmentPhases = TreatmentPhases.builder()
                .phaseNumber(request.getPhaseNumber())
                .description(request.getDescription())
                .listDentalServiceEntityOrder(request.getListDentalServicesEntityOrder())
                .listPrescriptionOrder(request.getListPrescriptionOrder())
                .cost(request.getCost())
                .status("Inprogress")
                .startDate(LocalDate.parse(request.getStartDate(), formatter))
                .endDate(LocalDate.parse(request.getEndDate(), formatter))
                .treatmentPlans(treatmentPlans)
                .build();

        if (request.getListImageXray() != null && !request.getListImageXray().isEmpty()) {
            for (MultipartFile file : request.getListImageXray()) {
                if (file.isEmpty()) continue;

                Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

                Image image = Image.builder()
                        .publicId((String) result.get("public_id"))
                        .url((String) result.get("secure_url"))
                        .type("treatmentPhasesXray")
                        .treatmentPhases(treatmentPhases)
                        .build();

                treatmentPhases.getListImage().add(image);
            }
        }

        if (request.getListImageFace() != null && !request.getListImageFace().isEmpty()) {
            for (MultipartFile file : request.getListImageFace()) {
                if (file.isEmpty()) continue;

                Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

                Image image = Image.builder()
                        .publicId((String) result.get("public_id"))
                        .url((String) result.get("secure_url"))
                        .type("treatmentPhasesFace")
                        .treatmentPhases(treatmentPhases)
                        .build();

                treatmentPhases.getListImage().add(image);
            }
        }

        if (request.getListImageTeeth() != null && !request.getListImageTeeth().isEmpty()) {
            for (MultipartFile file : request.getListImageTeeth()) {
                if (file.isEmpty()) continue;

                Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

                Image image = Image.builder()
                        .publicId((String) result.get("public_id"))
                        .url((String) result.get("secure_url"))
                        .type("treatmentPhasesTeeth")
                        .treatmentPhases(treatmentPhases)
                        .build();

                treatmentPhases.getListImage().add(image);
            }
        }

        treatmentPlans.setTotalCost(treatmentPlans.getTotalCost() + request.getCost());
        treatmentPhases.setTreatmentPlans(treatmentPlans);
        treatmentPlans.getListTreatmentPhases().add(treatmentPhases);

        treatmentPhasesRepository.save(treatmentPhases);
        log.info("Tiến trình: {} của phác đồ id: {} được thêm thành công.", treatmentPhases.getPhaseNumber(), treatmentPlansId);

        return TreatmentPhasesResponse.builder()
                .id(treatmentPhases.getId())
                .phaseNumber(request.getPhaseNumber())
                .description(request.getDescription())
                .listDentalServicesEntityOrder(request.getListDentalServicesEntityOrder())
                .listPrescriptionOrder(request.getListPrescriptionOrder())
                .cost(request.getCost())
                .status("Inprogress")
                .startDate(LocalDate.parse(request.getStartDate(), formatter))
                .endDate(LocalDate.parse(request.getEndDate(), formatter))
                .listImage(treatmentPhases.getListImage().stream().map(image -> ImageResponse.builder()
                        .publicId(image.getPublicId())
                        .type(image.getType())
                        .url(image.getUrl())
                        .build()
                ).toList())
                .build();
    }

    @PreAuthorize("hasAuthority('UPDATE_TREATMENT_PHASES','ADMIN')")
    @Transactional
    public TreatmentPhasesResponse updateTreatmentPhases(String treatmentPhasesId, TreatmentPhasesUpdateRequest request) throws IOException {
        TreatmentPhases treatmentPhases = treatmentPhasesRepository.findById(treatmentPhasesId).orElseThrow(() -> {
            log.error("Tiến trình điều trị Id: {} không tồn tại, cập nhật tiến trình điều trị thất bại.", treatmentPhasesId);
            throw new AppException(ErrorCode.TREATMENTPHASES_NOT_EXISTED);
        });

        TreatmentPlans treatmentPlans = treatmentPlansRepository.findById(treatmentPhases.getTreatmentPlans().getId()).orElseThrow(() -> {
            log.error("Phác đồ điều trị id: {} không tồn tại, cập nhật tiến trình điều trị thất bại.", treatmentPhases.getTreatmentPlans().getId());
            throw new AppException(ErrorCode.TREATMENTPLANS_NOT_EXISTED);
        });

        treatmentPlans.setTotalCost(treatmentPlans.getTotalCost() - treatmentPhases.getCost() + request.getCost());

        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        LocalDateTime inputDateTime = LocalDateTime.parse(request.getNextAppointment(), formatter);

        if(request.getNextAppointment() != null){
            if(treatmentPhases.getNextAppointment() != null){
                appointmentRepository.deleteByDoctorIdAndDateTime(treatmentPhases.getTreatmentPlans().getDoctor().getId(), treatmentPhases.getNextAppointment());
                bookingDateTimeRepository.deleteByDoctorIdAndDateTime(treatmentPhases.getTreatmentPlans().getDoctor().getId(), treatmentPhases.getNextAppointment());
            }

            if(bookingDateTimeRepository.existsByDoctorIdAndDateTime(treatmentPhases.getTreatmentPlans().getDoctor().getId(), inputDateTime)){
                log.error("Khung giờ: {} của bác sĩ id: {} đã được đặt, đặt lịch hẹn tiếp theo của tiến trình thất bại.", request.getNextAppointment(), treatmentPhases.getTreatmentPlans().getDoctor().getId());
                throw new AppException(ErrorCode.APPOINTMENT_EXISTED);
            }

            Appointment appointment = Appointment.builder()
                    .dateTime(inputDateTime)
                    .status("Scheduled")
                    .type("TreatmentPhases")
                    .notes("Khám theo tiến trình điều trị")
                    .doctor(treatmentPhases.getTreatmentPlans().getDoctor())
                    .patient(treatmentPhases.getTreatmentPlans().getPatient())
                    .build();

            BookingDateTime bookingDateTime = BookingDateTime.builder()
                    .dateTime(inputDateTime)
                    .doctor(treatmentPhases.getTreatmentPlans().getDoctor())
                    .build();

            appointmentRepository.save(appointment);
            bookingDateTimeRepository.save(bookingDateTime);
        }

        treatmentPhases.setPhaseNumber(request.getPhaseNumber());
        treatmentPhases.setDescription(request.getDescription());
        treatmentPhases.setStatus(request.getStatus());
        treatmentPhases.setStartDate(LocalDate.parse(request.getStartDate(), formatterDate));
        treatmentPhases.setEndDate(LocalDate.parse(request.getEndDate(), formatterDate));
        treatmentPhases.setNextAppointment(inputDateTime);
        treatmentPhases.setListDentalServiceEntityOrder(request.getListDentalServicesEntityOrder());
        treatmentPhases.setListPrescriptionOrder(request.getListPrescriptionOrder());

        if (request.getListDeleteImageByPublicId() != null && !request.getListDeleteImageByPublicId().isEmpty()) {
            for (String publicId : request.getListDeleteImageByPublicId()) {
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
                imageRepository.deleteByPublicId(publicId);

                treatmentPhases.getListImage().removeIf(img -> img.getPublicId().equals(publicId));
            }
        }

        if (request.getListImageXray() != null && !request.getListImageXray().isEmpty()) {
            for (MultipartFile file : request.getListImageXray()) {
                if (file.isEmpty()) continue;

                Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

                Image image = Image.builder()
                        .publicId((String) result.get("public_id"))
                        .url((String) result.get("secure_url"))
                        .type("treatmentPhasesXray")
                        .treatmentPhases(treatmentPhases)
                        .build();

                treatmentPhases.getListImage().add(image);
            }
        }

        if (request.getListImageFace() != null && !request.getListImageFace().isEmpty()) {
            for (MultipartFile file : request.getListImageFace()) {
                if (file.isEmpty()) continue;

                Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

                Image image = Image.builder()
                        .publicId((String) result.get("public_id"))
                        .url((String) result.get("secure_url"))
                        .type("treatmentPhasesFace")
                        .treatmentPhases(treatmentPhases)
                        .build();

                treatmentPhases.getListImage().add(image);
            }
        }

        if (request.getListImageTeeth() != null && !request.getListImageTeeth().isEmpty()) {
            for (MultipartFile file : request.getListImageTeeth()) {
                if (file.isEmpty()) continue;

                Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

                Image image = Image.builder()
                        .publicId((String) result.get("public_id"))
                        .url((String) result.get("secure_url"))
                        .type("treatmentPhasesTeeth")
                        .treatmentPhases(treatmentPhases)
                        .build();

                treatmentPhases.getListImage().add(image);
            }
        }

        treatmentPlans.getListTreatmentPhases().removeIf(treatmentPhasesRemove -> treatmentPhasesRemove.getId().equals(treatmentPhasesId));
        treatmentPlans.getListTreatmentPhases().add(treatmentPhases);

        treatmentPhasesRepository.save(treatmentPhases);
        log.info("Tiến trình điều trị id: {} được cập nhật thành công.", treatmentPhasesId);

        return TreatmentPhasesResponse.builder()
                .id(treatmentPhases.getId())
                .phaseNumber(request.getPhaseNumber())
                .description(request.getDescription())
                .listDentalServicesEntityOrder(request.getListDentalServicesEntityOrder())
                .listPrescriptionOrder(request.getListPrescriptionOrder())
                .cost(request.getCost())
                .status(request.getStatus())
                .startDate(LocalDate.parse(request.getStartDate(), formatterDate))
                .endDate(LocalDate.parse(request.getEndDate(), formatterDate))
                .nextAppointment(treatmentPhases.getNextAppointment())
                .listImage(treatmentPhases.getListImage().stream().map(image -> ImageResponse.builder()
                        .publicId(image.getPublicId())
                        .type(image.getType())
                        .url(image.getUrl())
                        .build()
                ).toList())
                .build();
    }

    @PreAuthorize("hasAuthority('GET_ALL_TREATMENT_PHASES','ADMIN')")
    public List<TreatmentPhasesResponse> getAllTreatmentPhasesOfTreatmentPlansId(String treatmentPlansId){
        if(!treatmentPlansRepository.existsById(treatmentPlansId)){
            log.error("Phác đồ điều trị id: {} không tồn tại, lấy danh sách tiến trình điều trị thất bại.", treatmentPlansId);
            throw new AppException(ErrorCode.TREATMENTPLANS_NOT_EXISTED);
        }

        return treatmentPhasesRepository.findAllByTreatmentPlansId(treatmentPlansId).stream().map(treatmentPhases -> TreatmentPhasesResponse.builder()
                        .id(treatmentPhases.getId())
                        .phaseNumber(treatmentPhases.getPhaseNumber())
                        .description(treatmentPhases.getDescription())
                        .listDentalServicesEntityOrder(treatmentPhases.getListDentalServiceEntityOrder())
                        .listPrescriptionOrder(treatmentPhases.getListPrescriptionOrder())
                        .cost(treatmentPhases.getCost())
                        .status(treatmentPhases.getStatus())
                        .startDate(treatmentPhases.getStartDate())
                        .endDate(treatmentPhases.getEndDate())
                        .nextAppointment(treatmentPhases.getNextAppointment())
                        .listImage(treatmentPhases.getListImage().stream().map(image -> ImageResponse.builder()
                                .publicId(image.getPublicId())
                                .type(image.getType())
                                .url(image.getUrl())
                                .build()
                        ).toList())
                        .build()
                ).toList();
    }

}
