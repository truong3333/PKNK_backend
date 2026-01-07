package com.example.pknk.controller.user;

import com.example.pknk.domain.dto.request.user.UserUpdateInfoRequest;
import com.example.pknk.domain.dto.request.user.UserUpdatePasswordRequest;
import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.domain.dto.response.user.UserResponse;
import com.example.pknk.service.user.UserService;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Tag(name = "User", description = "Quản lý người dùng")
public class UserController {
    UserService userService;

    @Operation(summary = "Lấy danh sách người dùng", description = "Lấy tất cả người dùng cho admin",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Danh sách user")
    @GetMapping
    ApiResponses<List<UserResponse>> getAllUsers(){
        return ApiResponses.<List<UserResponse>>builder()
                .code(1000)
                .result(userService.getAll())
                .build();
    }

    @Operation(summary = "Lấy thông tin người dùng", description = "Lấy thông tin người dùng bằng username ",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thông tin người dùng")
    @GetMapping("/{userId}")
    ApiResponses<UserResponse> getUserById(@PathVariable String userId){
        return  ApiResponses.<UserResponse>builder()
                .code(1000)
                .result(userService.getById(userId))
                .build();
    }

    @Operation(summary = "Cập nhật thông tin người dùng", description = "Cập nhật thông tin người dùng theo userId",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Cập nhật thông tin user thành công")
    @PutMapping("/updateInfo/{userId}")
    ApiResponses<UserResponse> updateInfoUser(@PathVariable String userId, @RequestBody UserUpdateInfoRequest request){
        return ApiResponses.<UserResponse>builder()
                .code(1000)
                .result(userService.updateInfo(userId, request))
                .build();
    }

    @Operation(summary = "Đổi mật khẩu", description = "Đổi mật khẩu",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Đổi mật khẩu thành công")
    @PutMapping("/updatePassword/{userId}")
    ApiResponses<String> updatePasswordUser(@PathVariable String userId, @RequestBody UserUpdatePasswordRequest request){
        return ApiResponses.<String>builder()
                .code(1000)
                .result(userService.updatePassword(userId,request))
                .build();
    }

    @Operation(summary = "Xem thông tin cá nhân", description = "Xem thông tin cá nhân ",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thông tin tài khoản")
    @GetMapping("/myInfo")
    ApiResponses<UserResponse> getMyInfo(){
        return ApiResponses.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @Operation(summary = "Vô hiệu hoá tài khoản", description = "Vô hiệu hoá tài khoản",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Vô hiệu hoá tài khoản thành công")
    @PutMapping("/disableUser/{userId}")
    ApiResponses<String> disableUser(@PathVariable String userId){
        return ApiResponses.<String>builder()
                .code(1000)
                .result(userService.disableUser(userId))
                .build();
    }

    @Operation(summary = "Huỷ vô hiệu hoá tài khoản", description = "Huỷ vô hiệu hoá tài khoản",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Huỷ vô hiệu hoá tài khoản thành công")
    @PutMapping("/enableUser/{userId}")
    ApiResponses<String> enableUser(@PathVariable String userId){
        return ApiResponses.<String>builder()
                .code(1000)
                .result(userService.enableUser(userId))
                .build();
    }
}
