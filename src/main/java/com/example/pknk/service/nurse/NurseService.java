package com.example.pknk.service.nurse;

import com.example.pknk.domain.dto.response.nurse.NurseInfoResponse;
import com.example.pknk.domain.dto.response.nurse.NursePickResponse;
import com.example.pknk.domain.entity.user.Nurse;
import com.example.pknk.exception.AppException;
import com.example.pknk.exception.ErrorCode;
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

    @PreAuthorize("hasAuthority('PICK_NURSE','ADMIN')")
    public List<NursePickResponse> getAllNurseForPick(){
        List<Nurse> listNurse = new ArrayList<>(nurseRepository.findAll());

        return listNurse.stream().map(nurse -> NursePickResponse.builder()
                .id(nurse.getId())
                .fullName(nurse.getUser().getUserDetail().getFullName())
                .build()
        ).toList();
    }

    @PreAuthorize("hasAuthority('GET_INFO_NURSE','ADMIN')")
    public NurseInfoResponse getInfoNurse(String nurseId){
        Nurse nurse = nurseRepository.findById(nurseId).orElseThrow(() -> {
            log.info("Y tá id: {} không tồn tại, lấy thông tin thất bại.", nurseId);
            throw new AppException(ErrorCode.NURSE_NOT_EXISTED);
        });

        return NurseInfoResponse.builder()
                .id(nurseId)
                .fullName(nurse.getUser().getUserDetail().getFullName())
                .phone(nurse.getUser().getUserDetail().getPhone())
                .build();
    }
}
