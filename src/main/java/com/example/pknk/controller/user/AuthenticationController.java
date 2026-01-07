package com.example.pknk.controller.user;

import com.example.pknk.domain.dto.request.user.*;
import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.domain.dto.response.user.AuthenticationResponse;
import com.example.pknk.domain.dto.response.user.IntrospectResponse;
import com.example.pknk.domain.dto.response.user.VerifyResetPasswordResponse;
import com.example.pknk.service.user.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Tag(name = "Authentication", description = "Quản lý tài khoản")
public class AuthenticationController {
    AuthenticationService authenticationService;

    @Operation(summary = "Đăng ký tài khoản bệnh nhân", description = "Đăng ký tài khoản bệnh nhân")
    @ApiResponse(responseCode = "200", description = "Đăng ký tài khoản bệnh nhân tành công")
    @PostMapping("/register/patient")
    ApiResponses<String> register(@RequestBody UserCreateRequest request){
        return ApiResponses.<String>builder()
                .code(1000)
                .result(authenticationService.createPatient(request))
                .build();
    }

    @Operation(summary = "Đăng ký tài khoản bác sĩ", description = "Đăng ký tài khoản bác sĩ",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Đăng ký tài khoản bác sĩ thành công")
    @PostMapping("/register/doctor")
    ApiResponses<String> register(@RequestBody DoctorCreateRequest request){
        return ApiResponses.<String>builder()
                .code(1000)
                .result(authenticationService.createDoctor(request))
                .build();
    }

    @Operation(summary = "Đăng ký tài khoản y tá", description = "Đăng ký tài khoản y tá",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Đăng ký tài khoản y tá thành công")
    @PostMapping("/register/nurse")
    ApiResponses<String> register(@RequestBody NurseCreateRequest request){
        return ApiResponses.<String>builder()
                .code(1000)
                .result(authenticationService.createNurse(request))
                .build();
    }

    @Operation(summary = "Đăng nhập", description = "Đăng nhập")
    @ApiResponse(responseCode = "200", description = "Đăng nhập thành công")
    @PostMapping("/login")
    ApiResponses<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        return ApiResponses.<AuthenticationResponse>builder()
                .code(1000)
                .result(authenticationService.login(request))
                .build();
    }

    @Operation(summary = "Xác thực email đăng ký tài khoản", description = "Mã xác thực email đăng ký tài khoản")
    @ApiResponse(responseCode = "200", description = "Xác thực thành công")
    @PostMapping("/verifiedCode/{email}")
    ApiResponses<String> verifiedCode(@PathVariable String email){
        return ApiResponses.<String>builder()
                .code(1000)
                .result(authenticationService.verifiedCode(email))
                .build();
    }

    @Operation(summary = "Đăng xuất", description = "Đăng xuất")
    @ApiResponse(responseCode = "200", description = "Đăng xuất thành công")
    @PostMapping("/logout")
    ApiResponses<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponses.<Void>builder()
                .code(1000)
                .build();
    }

    @Operation(summary = "Xác thực token", description = "Xác thực token")
    @ApiResponse(responseCode = "200", description = "Xác thực thành công")
    @PostMapping("/introspect")
    ApiResponses<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        return ApiResponses.<IntrospectResponse>builder()
                .code(1000)
                .result(authenticationService.introspect(request))
                .build();
    }

    @Operation(summary = "Làm mới token", description = "làm mới token",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "làm mới token thành công")
    @PostMapping("/refresh")
    ApiResponses<AuthenticationResponse> refresh(@RequestBody RefreshTokenRequest request) throws ParseException, JOSEException {
        return ApiResponses.<AuthenticationResponse>builder()
                .code(1000)
                .result(authenticationService.refresh(request))
                .build();
    }

    @Operation(summary = "Quên mật khẩu", description = "Quên mật khẩu")
    @ApiResponse(responseCode = "200", description = "Lấy lại mật khẩu thành công")
    @PostMapping("/forgotPassword")
    ApiResponses<String> forgotPassword(@RequestBody VerifyForgotPasswordRequest request){
        return ApiResponses.<String>builder()
                .code(1000)
                .result(authenticationService.forgotPassword(request))
                .build();
    }

    @Operation(summary = "Xác thực link quên mật khẩu", description = "Xác thực link quên mật khẩu")
    @ApiResponse(responseCode = "200", description = "Xác thực thành công")
    @PostMapping("/verifyResetPassword/{id}")
    ApiResponses<VerifyResetPasswordResponse> verifyResetPassword(@PathVariable String id){
        return ApiResponses.<VerifyResetPasswordResponse>builder()
                .code(1000)
                .result(authenticationService.verifyResetPassword(id))
                .build();
    }

    @Operation(summary = "Quên mật khẩu", description = "Quên mật khẩu")
    @ApiResponse(responseCode = "200", description = "Lấy lại mật khẩu thành công")
    @PostMapping("/resetPassword")
    ApiResponses<String> resetPassword(@RequestBody ResetPasswordRequest request){
        return ApiResponses.<String>builder()
                .code(1000)
                .result(authenticationService.resetPassword(request))
                .build();
    }

}
