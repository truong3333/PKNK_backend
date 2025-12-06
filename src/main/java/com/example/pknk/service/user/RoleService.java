package com.example.pknk.service.user;

import com.example.pknk.domain.dto.request.user.RoleRequest;
import com.example.pknk.domain.dto.response.user.PermissionResponse;
import com.example.pknk.domain.dto.response.user.RoleResponse;
import com.example.pknk.domain.entity.user.Permission;
import com.example.pknk.domain.entity.user.Role;
import com.example.pknk.exception.AppException;
import com.example.pknk.exception.ErrorCode;
import com.example.pknk.repository.user.PermissionRepository;
import com.example.pknk.repository.user.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public String create(RoleRequest request){
        if(roleRepository.existsById(request.getName())){
            log.error("Vai trò {} đã tồn tại, tao mới thất bại.",request.getName());
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }

        Role role = Role.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        roleRepository.save(role);
        log.info("Tạo vai trò: {} thành công.",request.getName());

        return "Tạo vai trò thành công.";
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<RoleResponse> getAll(){
        List<Role> roles = new ArrayList<>(roleRepository.findAll());

        return roles.stream().map(role -> RoleResponse.builder()
                .name(role.getName())
                .description(role.getDescription())
                .permissions(role.getPermissions().stream().map(permission -> PermissionResponse.builder()
                        .name(permission.getName())
                        .description(permission.getDescription())
                        .build()).toList()
                )
                .build()).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public String createPermission(String roleName, String permissionName){
        Role role = roleRepository.findById(roleName).orElseThrow(() -> {
            log.error("Vai trò: {} không tồn tại, thêm quyền cho vai trò thất bại.",roleName);
            throw new AppException(ErrorCode.ROLE_NOT_EXISTED);
        });

        Permission permission = permissionRepository.findById(permissionName).orElseThrow(() -> {
            log.error("Quyền: {} không tồn tại, thêm quyền cho vai trò thất bại.",permissionName);
            throw new AppException(ErrorCode.PERMISSION_NOT_EXISTED);
        });

        role.getPermissions().add(permission);
        permission.getRoles().add(role);
        log.info("Thêm quyền: {} cho vai trò: {} thành công", permissionName, roleName);

        return "Thêm quyền cho vai trò thành công";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public String deletePermission(String roleName, String permissionName){
        Role role = roleRepository.findById(roleName).orElseThrow(() -> {
            log.error("Vai trò: {} không tồn tại, xoá quyền của vai trò thất bại.",roleName);
            throw new AppException(ErrorCode.ROLE_NOT_EXISTED);
        });

        Permission permission = permissionRepository.findById(permissionName).orElseThrow(() -> {
            log.error("Quyền: {} không tồn tại, xoá quyền của vai trò thất bại.",permissionName);
            throw new AppException(ErrorCode.PERMISSION_NOT_EXISTED);
        });

        role.getPermissions().remove(permission);
        permission.getRoles().remove(role);
        log.info("Xoá quyền: {} cho vai trò: {} thành công", permissionName, roleName);

        return "Xoá quyền cho vai trò thành công";
    }

}
