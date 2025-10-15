package com.example.pknk.controller;

import com.example.pknk.domain.dto.request.user.*;
import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.domain.dto.response.user.AuthenticationResponse;
import com.example.pknk.domain.dto.response.user.IntrospectResponse;
import com.example.pknk.domain.dto.response.user.VerifyResetPasswordResponse;
import com.example.pknk.service.user.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/register/patient")
    ApiResponses<String> register(@RequestBody UserCreateRequest request){
        return ApiResponses.<String>builder()
                .code(1000)
                .result(authenticationService.createPatient(request))
                .build();
    }

    @PostMapping("/register/doctor")
    ApiResponses<String> register(@RequestBody DoctorCreateRequest request){
        return ApiResponses.<String>builder()
                .code(1000)
                .result(authenticationService.createDoctor(request))
                .build();
    }

    @PostMapping("/register/nurse")
    ApiResponses<String> register(@RequestBody NurseCreateRequest request){
        return ApiResponses.<String>builder()
                .code(1000)
                .result(authenticationService.createNurse(request))
                .build();
    }

    @PostMapping("/login")
    ApiResponses<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        return ApiResponses.<AuthenticationResponse>builder()
                .code(1000)
                .result(authenticationService.login(request))
                .build();
    }

    @PostMapping("/verifiedCode/{email}")
    ApiResponses<String> verifiedCode(@PathVariable String email){
        return ApiResponses.<String>builder()
                .code(1000)
                .result(authenticationService.verifiedCode(email))
                .build();
    }

    @PostMapping("/logout")
    ApiResponses<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponses.<Void>builder()
                .code(1000)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponses<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        return ApiResponses.<IntrospectResponse>builder()
                .code(1000)
                .result(authenticationService.introspect(request))
                .build();
    }

    @PostMapping("/forgotPassword")
    ApiResponses<String> forgotPassword(@RequestBody VerifyForgotPasswordRequest request){
        return ApiResponses.<String>builder()
                .code(1000)
                .result(authenticationService.forgotPassword(request))
                .build();
    }

    @PostMapping("/verifyResetPassword/{id}")
    ApiResponses<VerifyResetPasswordResponse> verifyResetPassword(@PathVariable String id){
        return ApiResponses.<VerifyResetPasswordResponse>builder()
                .code(1000)
                .result(authenticationService.verifyResetPassword(id))
                .build();
    }

    @PostMapping("/resetPassword")
    ApiResponses<String> resetPassword(@RequestBody ResetPasswordRequest request){
        return ApiResponses.<String>builder()
                .code(1000)
                .result(authenticationService.resetPassword(request))
                .build();
    }

}
