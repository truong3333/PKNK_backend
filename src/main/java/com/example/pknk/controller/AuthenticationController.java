package com.example.pknk.controller;

import com.example.pknk.domain.dto.request.user.AuthenticationRequest;
import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.domain.dto.response.user.AuthenticationResponse;
import com.example.pknk.service.user.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping
    ApiResponses<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        return ApiResponses.<AuthenticationResponse>builder()
                .code(1000)
                .result(authenticationService.login(request))
                .build();
    }
}
