package com.example.pknk.controller.user;

import com.example.pknk.domain.dto.request.user.UserUpdateInfoRequest;
import com.example.pknk.domain.dto.request.user.UserUpdatePasswordRequest;
import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.domain.dto.response.user.UserResponse;
import com.example.pknk.service.user.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserController {
    UserService userService;

    @GetMapping
    ApiResponses<List<UserResponse>> getAllUsers(){
        return ApiResponses.<List<UserResponse>>builder()
                .code(1000)
                .result(userService.getAll())
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponses<UserResponse> getUserById(@PathVariable String userId){
        return  ApiResponses.<UserResponse>builder()
                .code(1000)
                .result(userService.getById(userId))
                .build();
    }

    @PutMapping("/updateInfo/{userId}")
    ApiResponses<UserResponse> updateInfoUser(@PathVariable String userId, @RequestBody UserUpdateInfoRequest request){
        return ApiResponses.<UserResponse>builder()
                .code(1000)
                .result(userService.updateInfo(userId, request))
                .build();
    }

    @PutMapping("/updatePassword/{userId}")
    ApiResponses<String> updatePasswordUser(@PathVariable String userId, UserUpdatePasswordRequest request){
        return ApiResponses.<String>builder()
                .code(1000)
                .result(userService.updatePassword(userId,request))
                .build();
    }

    @GetMapping("/myInfo")
    ApiResponses<UserResponse> getMyInfo(){
        return ApiResponses.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponses<String> deleteUser(@PathVariable String userId){
        return ApiResponses.<String>builder()
                .code(1000)
                .result(userService.disableUser(userId))
                .build();
    }
}
