package com.example.pknk.service.user;

import com.example.pknk.domain.dto.request.user.UserUpdateInfoRequest;
import com.example.pknk.domain.dto.request.user.UserUpdatePasswordRequest;
import com.example.pknk.domain.dto.response.user.UserResponse;
import com.example.pknk.domain.entity.user.User;
import com.example.pknk.domain.entity.user.UserDetail;
import com.example.pknk.exception.AppException;
import com.example.pknk.exception.ErrorCode;
import com.example.pknk.repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class UserService {
        PasswordEncoder passwordEncoder;
        UserRepository userRepository;

        @PreAuthorize("hasRole('ADMIN')")
        public List<UserResponse> getAll(){
                List<User> listUser = new ArrayList<>(userRepository.findAll());

                log.info("Lấy danh sách người dùng thành công.");

                return listUser.stream().map(user -> UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .fullName(user.getUserDetail().getFullName())
                        .phone(user.getUserDetail().getPhone())
                        .email(user.getUserDetail().getEmail())
                        .address(user.getUserDetail().getAddress())
                        .gender(user.getUserDetail().getGender())
                        .dob(user.getUserDetail().getDob())
                        .build()
                ).toList();
        }


        @PreAuthorize("hasRole('ADMIN')")
        public UserResponse getById(String userId){
            User user = userRepository.findById(userId).orElseThrow(() -> {
                log.error("UserId: {} không tồn tại, xem thông tin người dùng thất bại", userId);
                throw new AppException(ErrorCode.USER_NOT_EXISTED);
            });

            log.info("UserId: {} lấy thông tin thành công theo id", userId);

            return UserResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .fullName(user.getUserDetail().getFullName())
                    .phone(user.getUserDetail().getPhone())
                    .email(user.getUserDetail().getEmail())
                    .address(user.getUserDetail().getAddress())
                    .gender(user.getUserDetail().getGender())
                    .dob(user.getUserDetail().getDob())
                    .build();
        }


        public UserResponse updateInfo(String userId, UserUpdateInfoRequest request){
                User user = userRepository.findById(userId).orElseThrow(() -> {
                        log.error("UserId: {} không tồn tại, cập nhật thông tin thất bại", userId);
                        throw new AppException(ErrorCode.USER_NOT_EXISTED);
                });

                UserDetail userDetail = user.getUserDetail();

                userDetail.setFullName(request.getFullName());
                userDetail.setPhone(request.getPhone());
                userDetail.setEmail(request.getEmail());
                userDetail.setAddress(request.getAddress());
                userDetail.setGender(request.getGender());
                userDetail.setDob(request.getDob());

                user.setUserDetail(userDetail);
                userDetail.setUser(user);

                userRepository.save(user);
                log.info("Cập nhật thông tin người dùng ID: {} thành công.",userId);

                return UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .fullName(user.getUserDetail().getFullName())
                        .phone(user.getUserDetail().getPhone())
                        .email(user.getUserDetail().getEmail())
                        .address(user.getUserDetail().getAddress())
                        .gender(user.getUserDetail().getGender())
                        .dob(user.getUserDetail().getDob())
                        .build();
        }

        public UserResponse getMyInfo(){
                var context = SecurityContextHolder.getContext();
                String name = context.getAuthentication().getName();

                User user = userRepository.findByUsername(name).orElseThrow(() -> {
                        log.error("Username: {} not found, get info failed.", name);
                        throw new AppException(ErrorCode.USER_NOT_EXISTED);
                });

                UserDetail userDetail = user.getUserDetail();
                
                if (userDetail == null) {
                    log.error("UserDetail is null for user: {}", name);
                    throw new AppException(ErrorCode.USER_NOT_EXISTED);
                }

                return UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .fullName(userDetail.getFullName())
                        .email(userDetail.getEmail())
                        .phone(userDetail.getPhone())
                        .address(userDetail.getAddress())
                        .gender(userDetail.getGender())
                        .dob(userDetail.getDob())
                        .build();
        }

        public String updatePassword(String userId, UserUpdatePasswordRequest request){
                User user = userRepository.findById(userId).orElseThrow(() -> {
                        log.error("UserId: {} không tồn tại, đổi mật khẩu thất bại", userId);
                        throw new AppException(ErrorCode.USER_NOT_EXISTED);
                });

                if(passwordEncoder.matches(request.getOldPassword(),user.getPassword())){
                        user.setPassword(passwordEncoder.encode(request.getPassword()));
                } else{
                        log.error("UserId: {} mật khẩu cũ không chính xác, đổi mật khẩu thất bại.", userId);
                        throw new AppException(ErrorCode.OLD_PASSWORD_INCORRECT);
                }

                userRepository.save(user);
                log.info("UserId: {} đổi mật khẩu thành công.", userId);

                return "Đôi mật khẩu thành công";
        }

        @PreAuthorize("hasRole('ADMIN')")
        public String disableUser(String userId){
                User user = userRepository.findById(userId).orElseThrow(() -> {
                        log.error("UserId: {} không tồn tại, đổi mật khẩu thất bại", userId);
                        throw new AppException(ErrorCode.USER_NOT_EXISTED);
                });

                user.setDisable(true);

                userRepository.save(user);
                log.info("UserId: {} vô hiệu hoá thành công.",userId);

                return "Vô hiệu hoá tài khoản thành công";
        }

        @PreAuthorize("hasRole('ADMIN')")
        public String enableUser(String userId){
                User user = userRepository.findById(userId).orElseThrow(() -> {
                        log.error("UserId: {} không tồn tại, đổi mật khẩu thất bại", userId);
                        throw new AppException(ErrorCode.USER_NOT_EXISTED);
                });

                user.setDisable(false);

                userRepository.save(user);
                log.info("UserId: {} mở khoá thành công.",userId);

                return "Mở khoá tài khoản thành công";
        }
}
