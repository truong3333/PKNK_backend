package com.example.pknk.service.user;

import com.example.pknk.domain.dto.request.user.PermissionRequest;
import com.example.pknk.domain.dto.response.user.PermissionResponse;
import com.example.pknk.domain.entity.user.Permission;
import com.example.pknk.exception.AppException;
import com.example.pknk.exception.ErrorCode;
import com.example.pknk.repository.user.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class PermissionService {
    PermissionRepository permissionRepository;

    public String create(PermissionRequest request){
        if(permissionRepository.existsById(request.getName())){
            log.error("Quyền: {} đã tồn tại, thêm mới thất bại.", request.getName());
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }

        Permission permission = Permission.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        permissionRepository.save(permission);
        log.info("Tạo quyền: {} thành công.", request.getName());


        return "Tạo quyền thành công.";
    }

    public List<PermissionResponse> getAll(){
        List<Permission> permissions = new ArrayList<>(permissionRepository.findAll());

        log.info("Lấy danh sách quyền thành công.");

        return permissions.stream().map(permission -> PermissionResponse.builder()
                .name(permission.getName())
                .description(permission.getDescription())
                .build()
        ).toList();
    }

}

