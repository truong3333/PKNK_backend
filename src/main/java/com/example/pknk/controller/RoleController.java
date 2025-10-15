package com.example.pknk.controller;

import com.example.pknk.domain.dto.request.user.RoleRequest;
import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.domain.dto.response.user.RoleResponse;
import com.example.pknk.service.user.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponses<String> createRole(@RequestBody RoleRequest request){
        return ApiResponses.<String>builder()
                .code(1000)
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponses<List<RoleResponse>> getAllRole(){
        return ApiResponses.<List<RoleResponse>>builder()
                .code(1000)
                .result(roleService.getAll())
                .build();
    }

    @PostMapping("/{roleName}/addPermission/{permissionName}")
    ApiResponses<String> createPermissionToRole(@PathVariable String roleName, @PathVariable String permissionName){
        return ApiResponses.<String>builder()
                .code(1000)
                .result(roleService.createPermission(roleName, permissionName))
                .build();
    }

    @PostMapping("/{roleName}/deletePermission/{permissionName}")
    ApiResponses<String> deletePermissionOfRole(@PathVariable String roleName, @PathVariable String permissionName){
        return ApiResponses.<String>builder()
                .code(1000)
                .result(roleService.deletePermission(roleName, permissionName))
                .build();
    }
}
