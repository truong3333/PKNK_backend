package com.example.pknk.controller.user;

import com.example.pknk.domain.dto.request.user.PermissionRequest;
import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.domain.dto.response.user.PermissionResponse;
import com.example.pknk.service.user.PermissionService;
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
@RequestMapping("api/v1/permission")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Tag(name = "Permission", description = "Quản lý quyền")
public class PermissionController {
    PermissionService permissionService;

    @Operation(summary = "Thêm quyền", description = "Thêm quyền mới",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thêm quyền mới thành công")
    @PostMapping
    ApiResponses<String> createPermission(@RequestBody PermissionRequest request){
        return ApiResponses.<String>builder()
                .code(1000)
                .result(permissionService.create(request))
                .build();
    }

    @Operation(summary = "Lấy danh sách quyền", description = "Lấy danh sách quyền",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Danh sách quyèn")
    @GetMapping
    ApiResponses<List<PermissionResponse>> getAllPermission(){
        return ApiResponses.<List<PermissionResponse>>builder()
                .code(1000)
                .result(permissionService.getAll())
                .build();
    }
}
