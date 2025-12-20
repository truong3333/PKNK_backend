package com.example.pknk.service.user;

import com.example.pknk.domain.dto.request.user.RoleRequest;
import com.example.pknk.domain.dto.response.user.PermissionResponse;
import com.example.pknk.domain.dto.response.user.RoleResponse;
import com.example.pknk.domain.entity.user.Doctor;
import com.example.pknk.domain.entity.user.Permission;
import com.example.pknk.domain.entity.user.Role;
import com.example.pknk.exception.AppException;
import com.example.pknk.exception.ErrorCode;
import com.example.pknk.repository.doctor.DoctorRepository;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    DoctorRepository doctorRepository;

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

    @PreAuthorize("hasRole('ADMIN')")
    public String updateLevelDoctor(String doctorId){
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> {
            log.info("Bác sĩ id: {} không tồn tại, nâng cấp vai trò thất bại.", doctorId);
            throw new AppException(ErrorCode.DOCTOR_NOT_EXISTED);
        });

        Role role = roleRepository.findById("DOCTORLV2").orElseThrow(() -> {
            log.error("Vai trò: DOCTORLV2 không tồn tại, nâng cấp vai trò thất bại.");
            throw new AppException(ErrorCode.ROLE_NOT_EXISTED);
        });

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        doctor.getUser().setRoles(roles);

        doctorRepository.save(doctor);
        log.info("Bác sĩ id: {} nâng cấp vai trò thành công.", doctorId);

        return "Nâng cấp vai trò thành công";
    }

    @PreAuthorize("hasRole('ADMIN')")
    public String updateLevelDoctorByUserId(String userId){
        // Find doctor by user ID
        Doctor doctor = doctorRepository.findByUserId(userId).orElseThrow(() -> {
            log.info("User id: {} không tồn tại hoặc không phải là bác sĩ, nâng cấp vai trò thất bại.", userId);
            throw new AppException(ErrorCode.DOCTOR_NOT_EXISTED);
        });

        Role role = roleRepository.findById("DOCTORLV2").orElseThrow(() -> {
            log.error("Vai trò: DOCTORLV2 không tồn tại, nâng cấp vai trò thất bại.");
            throw new AppException(ErrorCode.ROLE_NOT_EXISTED);
        });

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        doctor.getUser().setRoles(roles);

        doctorRepository.save(doctor);
        log.info("Bác sĩ với user id: {} nâng cấp vai trò thành công.", userId);

        return "Nâng cấp vai trò thành công";
    }

}
