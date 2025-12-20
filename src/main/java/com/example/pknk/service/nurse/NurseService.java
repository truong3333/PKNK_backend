package com.example.pknk.service.nurse;

import com.example.pknk.domain.dto.response.clinic.AppointmentResponse;
import com.example.pknk.domain.dto.response.nurse.NurseInfoResponse;
import com.example.pknk.domain.dto.response.nurse.NursePickResponse;
import com.example.pknk.domain.entity.clinic.Appointment;
import com.example.pknk.domain.entity.user.Nurse;
import com.example.pknk.exception.AppException;
import com.example.pknk.exception.ErrorCode;
import com.example.pknk.repository.clinic.AppointmentRepository;
import com.example.pknk.repository.nurse.NurseRepository;
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
public class NurseService {
    NurseRepository nurseRepository;
    AppointmentRepository appointmentRepository;

    @PreAuthorize("hasAnyAuthority('PICK_NURSE','ADMIN')")
    public List<NursePickResponse> getAllNurseForPick(){
        List<Nurse> listNurse = new ArrayList<>(nurseRepository.findAll());

        return listNurse.stream().map(nurse -> NursePickResponse.builder()
                .id(nurse.getId())
                .fullName(nurse.getUser().getUserDetail().getFullName())
                .build()
        ).toList();
    }

    @PreAuthorize("hasAnyAuthority('GET_INFO_NURSE','ADMIN') OR hasRole('NURSE')")
    public NurseInfoResponse getInfoNurse(String nurseId){
        Nurse nurse = nurseRepository.findById(nurseId).orElseThrow(() -> {
            log.info("Y tá id: {} không tồn tại, lấy thông tin thất bại.", nurseId);
            throw new AppException(ErrorCode.NURSE_NOT_EXISTED);
        });

        var userDetail = nurse.getUser().getUserDetail();

        return NurseInfoResponse.builder()
                .id(nurseId)
                .fullName(userDetail.getFullName())
                .phone(userDetail.getPhone())
                .email(userDetail.getEmail())
                .address(userDetail.getAddress())
                .gender(userDetail.getGender())
                .dob(userDetail.getDob() != null ? userDetail.getDob().toString() : null)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('GET_INFO_NURSE','ADMIN') OR hasRole('NURSE','ADMIN')")
    public NurseInfoResponse getInfoNurseByUserId(String userId){
        Nurse nurse = nurseRepository.findByUserId(userId).orElseThrow(() -> {
            log.info("User id: {} không tồn tại hoặc không phải là y tá, lấy thông tin thất bại.", userId);
            throw new AppException(ErrorCode.NURSE_NOT_EXISTED);
        });

        var userDetail = nurse.getUser().getUserDetail();

        return NurseInfoResponse.builder()
                .id(nurse.getId())
                .fullName(userDetail.getFullName())
                .phone(userDetail.getPhone())
                .email(userDetail.getEmail())
                .address(userDetail.getAddress())
                .gender(userDetail.getGender())
                .dob(userDetail.getDob() != null ? userDetail.getDob().toString() : null)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('NOTIFICATION_APPOINMENT','ADMIN')")
    public AppointmentResponse notificationUpdateAppointment(String appointmentId){
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> {
            log.error("Lịch hẹn id: {} không tồn tại, cập nhật trạng thái thông báo thất bại.", appointmentId);
            throw new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED);
        });

        if("Done".equals(appointment.getNotification())){
            log.error("Lịch hẹn id: {} đã được y tá khác thông báo tới cả bác sĩ và bệnh nhân.", appointmentId);
            throw new AppException(ErrorCode.APPOINTMENT_NOTIFICATION_EXISTED);
        }
        appointment.setNotification("Done");

        appointmentRepository.save(appointment);
        log.info("Lịch hẹn id: {} cập nhật trạng thái thông báo thành công.", appointmentId);

        return AppointmentResponse.builder()
                .id(appointmentId)
                .dateTime(appointment.getDateTime().toString())
                .status(appointment.getStatus())
                .type(appointment.getType())
                .notes(appointment.getNotes())
                .listDentalServicesEntity(appointment.getListDentalServicesEntity())
                .doctorId(appointment.getDoctor().getId())
                .patientId(appointment.getPatient().getId())
                .notification(appointment.getNotification())
                .build();
    }
}
