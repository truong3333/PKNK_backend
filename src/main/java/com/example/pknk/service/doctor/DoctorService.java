package com.example.pknk.service.doctor;

import com.example.pknk.domain.dto.response.clinic.AppointmentResponse;
import com.example.pknk.domain.entity.user.Doctor;
import com.example.pknk.domain.entity.user.User;
import com.example.pknk.exception.AppException;
import com.example.pknk.exception.ErrorCode;
import com.example.pknk.repository.clinic.AppointmentRepository;
import com.example.pknk.repository.doctor.DoctorRepository;
import com.example.pknk.repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DoctorService {
    DoctorRepository doctorRepository;
    AppointmentRepository appointmentRepository;
    UserRepository userRepository;

    public List<AppointmentResponse> getAppointmentScheduledOfDoctor(String doctorId){
        if(!doctorRepository.existsById(doctorId)){
            log.error("Bác sĩ id: {} không tồn tại, lấy danh sách lịch hẹn thất bại.", doctorId);
            throw new AppException(ErrorCode.DOCTOR_NOT_EXISTED);
        }

        return appointmentRepository.findAllByDoctorIdAndStatus(doctorId, "Scheduled").stream().map(appointment -> AppointmentResponse.builder()
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
}
