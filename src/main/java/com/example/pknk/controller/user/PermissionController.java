package com.example.pknk.controller.user;

import com.example.pknk.domain.dto.request.user.PermissionRequest;
import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.domain.dto.response.user.PermissionResponse;
import com.example.pknk.service.user.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/permission")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiResponses<String> createPermission(@RequestBody PermissionRequest request){
        return ApiResponses.<String>builder()
                .code(1000)
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    ApiResponses<List<PermissionResponse>> getAllPermission(){
        return ApiResponses.<List<PermissionResponse>>builder()
                .code(1000)
                .result(permissionService.getAll())
                .build();
    }
}
