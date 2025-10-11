package com.example.pknk.controller;

import com.example.pknk.domain.dto.request.user.AuthenticationRequest;
import com.example.pknk.domain.dto.request.user.UserCreateRequest;
import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.domain.dto.response.user.AuthenticationResponse;
import com.example.pknk.service.user.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

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
}
