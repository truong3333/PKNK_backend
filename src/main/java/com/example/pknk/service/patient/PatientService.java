package com.example.pknk.service.patient;

import com.example.pknk.domain.dto.request.patient.AppointmentRequest;
import com.example.pknk.domain.dto.request.patient.EmergencyContactRequest;
import com.example.pknk.domain.dto.request.patient.MedicalInformationRequest;
import com.example.pknk.domain.dto.response.clinic.AppointmentResponse;
import com.example.pknk.domain.dto.response.clinic.ExaminationResponse;
import com.example.pknk.domain.dto.response.clinic.ImageResponse;
import com.example.pknk.domain.dto.response.patient.BookingDateTimeResponse;
import com.example.pknk.domain.dto.response.patient.EmergencyContactResponse;
import com.example.pknk.domain.dto.response.patient.MedicalInformationResponse;
import com.example.pknk.domain.dto.response.patient.PatientResponse;
import com.example.pknk.domain.entity.clinic.Appointment;
import com.example.pknk.domain.entity.clinic.BookingDateTime;
import com.example.pknk.domain.entity.clinic.Examination;
import com.example.pknk.domain.entity.user.Doctor;
import com.example.pknk.domain.entity.user.Patient;
import com.example.pknk.domain.entity.user.User;
import com.example.pknk.exception.AppException;
import com.example.pknk.exception.ErrorCode;
import com.example.pknk.repository.clinic.AppointmentRepository;
import com.example.pknk.repository.clinic.ExaminationRepository;
import com.example.pknk.repository.doctor.DoctorRepository;
import com.example.pknk.repository.doctor.BookingDateTimeRepository;
import com.example.pknk.repository.patient.PatientRepository;
import com.example.pknk.repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class PatientService {
        UserRepository userRepository;
        DoctorRepository doctorRepository;
        PatientRepository patientRepository;
        BookingDateTimeRepository bookingDateTimeRepository;
        AppointmentRepository appointmentRepository;
        ExaminationRepository examinationRepository;

        public PatientResponse getBasicInfo(String patientId){
            Patient patient = patientRepository.findById(patientId).orElseThrow(() -> {
                log.error("Bệnh nhân id: {} không tồn tại, lấy thông tin cơ bản thất bại.", patientId);
                throw new AppException(ErrorCode.PATIENT_NOT_EXISTED);
            });

            return PatientResponse.builder()
                    .id(patient.getId())
                    .fullName(patient.getUser().getUserDetail().getFullName())
                    .email(patient.getUser().getUserDetail().getEmail())
                    .phone(patient.getUser().getUserDetail().getPhone())
                    .address(patient.getUser().getUserDetail().getAddress())
                    .gender(patient.getUser().getUserDetail().getGender())
                    .dob(patient.getUser().getUserDetail().getDob())
                    .emergencyContactName(patient.getEmergencyContactName())
                    .emergencyPhoneNumber(patient.getEmergencyPhoneNumber())
                    .bloodGroup(patient.getBloodGroup())
                    .allergy(patient.getAllergy())
                    .medicalHistory(patient.getMedicalHistory())
                    .build();
        }
        public PatientResponse getMyPatientInfo() {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Patient patient = patientRepository.findByUserUsername(username)
                    .orElseThrow(() -> new AppException(ErrorCode.PATIENT_NOT_EXISTED));
            return PatientResponse.builder()
                    .id(patient.getId())
                    .fullName(patient.getUser().getUserDetail().getFullName())
                    .email(patient.getUser().getUserDetail().getEmail())
                    .phone(patient.getUser().getUserDetail().getPhone())
                    .address(patient.getUser().getUserDetail().getAddress())
                    .gender(patient.getUser().getUserDetail().getGender())
                    .dob(patient.getUser().getUserDetail().getDob())
                    .emergencyContactName(patient.getEmergencyContactName())
                    .emergencyPhoneNumber(patient.getEmergencyPhoneNumber())
                    .bloodGroup(patient.getBloodGroup())
                    .allergy(patient.getAllergy())
                    .medicalHistory(patient.getMedicalHistory())
                    .build();
        }
        public EmergencyContactResponse updateEmergencyContact(String patientId, EmergencyContactRequest request){
            Patient patient = patientRepository.findById(patientId).orElseThrow(() -> {
                log.error("Bệnh nhân id: {} không tồn tại, cập nhật thông tin liên hệ khẩn cấp thất bại.", patientId);
                throw new AppException(ErrorCode.PATIENT_NOT_EXISTED);
            });

            patient.setEmergencyContactName(request.getEmergencyContactName());
            patient.setEmergencyPhoneNumber(request.getEmergencyPhoneNumber());

            patientRepository.save(patient);
            log.info("Bệnh nhân id: {} cập nhật thông tin liên hệ khẩn cấp thành công", patientId);

            return EmergencyContactResponse.builder()
                    .emergencyContactName(request.getEmergencyContactName())
                    .emergencyPhoneNumber(request.getEmergencyPhoneNumber())
                    .build();
        }

        public MedicalInformationResponse updateMedicalInformation(String patientId, MedicalInformationRequest request){
            Patient patient = patientRepository.findById(patientId).orElseThrow(() -> {
                log.error("Bệnh nhân id: {} không tồn tại, cập nhật thông tin y tế thất bại.", patientId);
                throw new AppException(ErrorCode.PATIENT_NOT_EXISTED);
            });

            patient.setBloodGroup(request.getBloodGroup());
            patient.setAllergy(request.getAllergy());
            patient.setMedicalHistory(request.getMedicalHistory());

            patientRepository.save(patient);
            log.info("Bệnh nhân id: {} cập nhật thông tin y tế thành công", patientId);

            return MedicalInformationResponse.builder()
                    .bloodGroup(request.getBloodGroup())
                    .allergy(request.getAllergy())
                    .medicalHistory(request.getMedicalHistory())
                    .build();
        }

        public AppointmentResponse bookingAppointment(AppointmentRequest request){
            Doctor doctor = doctorRepository.findById(request.getDoctorId()).orElseThrow(() -> {
                log.error("Bác sĩ id: {} không tồn tại, đặt lịch hẹn thất bại.", request.getDoctorId());
                throw new AppException(ErrorCode.DOCTOR_NOT_EXISTED);
            });

            User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> {
                log.error("Username: {} không tồn tại, cập nhật thông tin thất bại", SecurityContextHolder.getContext().getAuthentication().getName());
                throw new AppException(ErrorCode.USER_NOT_EXISTED);
            });

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
            LocalDateTime inputDateTime = LocalDateTime.parse(request.getDateTime(), formatter);
            if(bookingDateTimeRepository.existsByDoctorIdAndDateTime(doctor.getId(), inputDateTime)){
                log.error("Khung giờ: {} của bác sĩ id: {} đã được đặt, đặt lịch hẹn thất bại.", request.getDateTime(), request.getDoctorId());
                throw new AppException(ErrorCode.APPOINTMENT_EXISTED);
            }

            Appointment appointment = Appointment.builder()
                    .dateTime(inputDateTime)
                    .status("Scheduled")
                    .type(request.getType())
                    .notes(request.getNotes())
                    .listDentalServicesEntity(request.getListDentalServicesEntity())
                    .doctor(doctor)
                    .patient(user.getPatient())
                    .build();

            BookingDateTime bookingDateTime = BookingDateTime.builder()
                    .dateTime(inputDateTime)
                    .doctor(doctor)
                    .build();

            appointmentRepository.save(appointment);
            bookingDateTimeRepository.save(bookingDateTime);
            log.info("Đặt lịch khám thành công giữa bệnh nhân username: {} và bác sĩ username: {}", user.getUsername(), doctor.getUser().getUsername());

            return AppointmentResponse.builder()
                    .id(appointment.getId())
                    .dateTime(request.getDateTime())
                    .status("Scheduled")
                    .type(request.getType())
                    .notes(request.getNotes())
                    .listDentalServicesEntity(request.getListDentalServicesEntity())
                    .doctorFullName(doctor.getUser().getUserDetail().getFullName())
                    .doctorSpecialization(doctor.getSpecialization())
                    .build();
        }

        public List<BookingDateTimeResponse> getAllBookingOfDoctor(String doctorId){
            List<BookingDateTime> listBooking = new ArrayList<>(bookingDateTimeRepository.findAllByDoctorId(doctorId));

            return listBooking.stream().map(bookingDateTime -> BookingDateTimeResponse.builder()
                    .dateTime(bookingDateTime.getDateTime())
                    .build()
            ).toList();
        }

        @Transactional
        public AppointmentResponse cancelBookingAppointment(String appointmentId){
            Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> {
                log.error("Lịch hẹn id: {} không tồn tại, huỷ lịch hẹn thất bại.", appointmentId);
                throw new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED);
            });

            appointment.setStatus("Cancel");

            bookingDateTimeRepository.deleteByDoctorIdAndDateTime(appointment.getDoctor().getId(), appointment.getDateTime());

            appointmentRepository.save(appointment);
            log.info("Huỷ lịch hẹn id: {} thành công.", appointmentId);

            return AppointmentResponse.builder()
                    .id(appointmentId)
                    .dateTime(appointment.getDateTime().toString())
                    .status("Cancel")
                    .type(appointment.getType())
                    .notes(appointment.getNotes())
                    .listDentalServicesEntity(appointment.getListDentalServicesEntity())
                    .doctorFullName(appointment.getDoctor().getUser().getUserDetail().getFullName())
                    .doctorSpecialization(appointment.getDoctor().getSpecialization())
                    .build();
        }

        @Transactional
        public AppointmentResponse updateBookingAppointment(String appointmentId, AppointmentRequest request){
            Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> {
                log.error("Lịch hẹn id: {} không tồn tại, huỷ lịch hẹn thất bại.", appointmentId);
                throw new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED);
            });

            Doctor doctor = doctorRepository.findById(request.getDoctorId()).orElseThrow(() -> {
                log.error("Bác sĩ id: {} không tồn tại, đặt lịch khám thất bại.", request.getDoctorId());
                throw new AppException(ErrorCode.DOCTOR_NOT_EXISTED);
            });

            User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> {
                log.error("Username: {} không tồn tại, cập nhật thông tin thất bại", SecurityContextHolder.getContext().getAuthentication().getName());
                throw new AppException(ErrorCode.USER_NOT_EXISTED);
            });

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
            LocalDateTime inputDateTime = LocalDateTime.parse(request.getDateTime(), formatter);
            if(bookingDateTimeRepository.existsByDoctorIdAndDateTime(request.getDoctorId(), inputDateTime)){
                log.error("Khung giờ: {} của bác sĩ id: {} đã được đặt, đặt lịch hẹn thất bại.", request.getDateTime(), request.getDoctorId());
                throw new AppException(ErrorCode.APPOINTMENT_EXISTED);
            }

            if(!appointment.getDoctor().getId().equals(request.getDoctorId()) || !appointment.getDateTime().equals(inputDateTime)){
                bookingDateTimeRepository.deleteByDoctorIdAndDateTime(appointment.getDoctor().getId(), appointment.getDateTime());
                appointment.getDoctor().getListAppointment().remove(appointment);
            }

            appointment.setDateTime(inputDateTime);
            appointment.setType(request.getType());
            appointment.setNotes(request.getNotes());
            appointment.setListDentalServicesEntity(request.getListDentalServicesEntity());
            appointment.setDoctor(doctor);
            appointment.setPatient(user.getPatient());

            BookingDateTime bookingDateTime = BookingDateTime.builder()
                    .dateTime(inputDateTime)
                    .doctor(doctor)
                    .build();

            appointmentRepository.save(appointment);
            bookingDateTimeRepository.save(bookingDateTime);
            log.info("Cập nhật lịch khám thành công giữa bệnh nhân username: {} và bác sĩ username: {}", user.getUsername(), doctor.getUser().getUsername());

            return AppointmentResponse.builder()
                    .id(appointmentId)
                    .dateTime(request.getDateTime())
                    .status("Scheduled")
                    .type(request.getType())
                    .notes(request.getNotes())
                    .listDentalServicesEntity(request.getListDentalServicesEntity())
                    .doctorFullName(doctor.getUser().getUserDetail().getFullName())
                    .doctorSpecialization(doctor.getSpecialization())
                    .build();
        }

        public List<AppointmentResponse> getMyAppointment(){
            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            User user = userRepository.findByUsername(username).orElseThrow(() -> {
                log.error("Username: {} không tồn tại, Lấy danh sách lịch hẹn thất bại.", username);
                throw new AppException(ErrorCode.USER_NOT_EXISTED);
            });

            return appointmentRepository.findAllByPatientId(user.getPatient().getId()).stream().map(appointment -> AppointmentResponse.builder()
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

        public List<ExaminationResponse> getMyExamination(){
            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            User user = userRepository.findByUsername(username).orElseThrow(() -> {
                log.error("Username: {} không tồn tại, Lấy danh sách kết quả thất bại.", username);
                throw new AppException(ErrorCode.USER_NOT_EXISTED);
            });

            List<Appointment> listAppointment = new ArrayList<>(appointmentRepository.findAllByPatientId(user.getPatient().getId()));

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
                .listDentalServicesEntityOrder(examination.getListDentalServicesEntityOrder())
                .listPrescriptionOrder(examination.getListPrescriptionOrder())
                .listImage(examination.getListImage().stream().map(image -> ImageResponse.builder()
                        .publicId(image.getPublicId())
                        .type(image.getType())
                        .url(image.getUrl())
                        .build()
                ).toList())
                .createAt(examination.getAppointment().getDateTime().toLocalDate())
                .build();
    }

}
