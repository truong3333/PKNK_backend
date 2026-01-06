package com.example.pknk.controller.user;

import com.example.pknk.domain.dto.request.user.RoleRequest;
import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.domain.dto.response.user.RoleResponse;
import com.example.pknk.service.user.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Tag(name = "Role", description = "Quản lý vai trò")
public class RoleController {
    RoleService roleService;

    @Operation(summary = "Thêm vai trò", description = "Thêm vai trò mới",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thêm vai trò thành công")
    @PostMapping
    ApiResponses<String> createRole(@RequestBody RoleRequest request){
        return ApiResponses.<String>builder()
                .code(1000)
                .result(roleService.create(request))
                .build();
    }

    @Operation(summary = "Xem danh sách vai trò", description = "Xem danh sách vai trò",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Danh sách vai trò")
    @GetMapping
    ApiResponses<List<RoleResponse>> getAllRole(){
        return ApiResponses.<List<RoleResponse>>builder()
                .code(1000)
                .result(roleService.getAll())
                .build();
    }

    @Operation(summary = "Thêm quyền cho vai trò", description = "Thêm quyền cho vai trò",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thêm quyền cho vai trò thành công")
    @PostMapping("/{roleName}/addPermission/{permissionName}")
    ApiResponses<String> createPermissionToRole(@PathVariable String roleName, @PathVariable String permissionName){
        return ApiResponses.<String>builder()
                .code(1000)
                .result(roleService.createPermission(roleName, permissionName))
                .build();
    }

    @Operation(summary = "Xoá quyền cho vai trò", description = "Xoá quyền cho vai trò",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Xoá quyền cho vai trò thành công")
    @PostMapping("/{roleName}/deletePermission/{permissionName}")
    ApiResponses<String> deletePermissionOfRole(@PathVariable String roleName, @PathVariable String permissionName){
        return ApiResponses.<String>builder()
                .code(1000)
                .result(roleService.deletePermission(roleName, permissionName))
                .build();
    }

    @Operation(summary = "Nâng cấp vai trò bác sĩ", description = "Nâng cấp vai trò bác sĩ lên bác sĩ cấp cao",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Nâng cấp vai trò thành công")
    @PostMapping("/{doctorId}/updateLevel")
    ApiResponses<String> updateLevelDoctor(@PathVariable String doctorId){
        return ApiResponses.<String>builder()
                .code(1000)
                .result(roleService.updateLevelDoctor(doctorId))
                .build();
    }

    @Operation(summary = "Nâng cấp vai trò bác sĩ", description = "Nâng cấp vai trò bác sĩ lên bác sĩ cấp cao theo userId",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Nâng cấp vai trò thành công")
    @PostMapping("/user/{userId}/updateLevel")
    ApiResponses<String> updateLevelDoctorByUserId(@PathVariable String userId){
        return ApiResponses.<String>builder()
                .code(1000)
                .result(roleService.updateLevelDoctorByUserId(userId))
                .build();
    }
}
