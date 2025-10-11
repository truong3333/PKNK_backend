package com.example.pknk.service.user;

import com.example.pknk.domain.dto.request.user.AuthenticationRequest;
import com.example.pknk.domain.dto.request.user.UserCreateRequest;
import com.example.pknk.domain.dto.response.user.AuthenticationResponse;
import com.example.pknk.domain.entity.user.*;
import com.example.pknk.exception.AppException;
import com.example.pknk.exception.ErrorCode;
import com.example.pknk.repository.PatientRepository;
import com.example.pknk.repository.RoleRepository;
import com.example.pknk.repository.UserRepository;
import com.example.pknk.repository.VerificationCodeRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class AuthenticationService {
    PasswordEncoder passwordEncoder;
    JavaMailSender mailSender;

    UserRepository userRepository;
    VerificationCodeRepository verificationCodeRepository;
    PatientRepository patientRepository;
    RoleRepository roleRepository;

    @Value("${jwt.signerKey}")
    @NonFinal
    String signerKey;

    public String createPatient(UserCreateRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            log.error("Tài khoản: {} đã tồn tại, đăng kí tài khoản bệnh nhân thất bại", request.getUsername());
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        Role role = roleRepository.findById("PATIENT").orElseThrow(() -> {
            log.error("Role: PATIENT không tồn tại, đăng kí tài khoản bệnh nhân thất bại.");
            throw new AppException(ErrorCode.ROLE_NOT_EXISTED);
        });

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        UserDetail userDetail = new UserDetail();
        userDetail.setFullName(request.getFullName());
        userDetail.setEmail(request.getEmail());
        userDetail.setPhone(request.getPhone());
        userDetail.setAddress(request.getAddress());
        userDetail.setGender(request.getGender());
        userDetail.setDob(request.getDob());
        userDetail.setCreateAt(LocalDate.now());

        Patient patient = Patient.builder()
                .build();

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .disable(false)
                .roles(roles)
                .userDetail(userDetail)
                .patient(patient)
                .build();

        userDetail.setUser(user);
        patient.setUser(user);

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(request.getEmail()).orElseThrow(() -> {
            log.error("Email: {} chưa có mã xác thực đăng kí tài khoản, đăng kí tài khoản bệnh nhân thất bại.", request.getEmail());
            throw new AppException(ErrorCode.VERIFIED_CODE_NOT_EXISTED);
        });

        if(request.getVerifiedCode().equals(verificationCode.getCode()) && verificationCode.getExpiredAt().isAfter(LocalDateTime.now())){
            userRepository.save(user);
            log.info("Tài khoản: {} đăng kí thành công.", request.getUsername());
        }else{
            log.error("Mã xác thực của email: {} không hợp lệ hoặc đã hết hạn, đăng kí tài khoản bệnh nhân thất bại.", request.getEmail());
            throw new AppException(ErrorCode.VERIFIED_CODE_INVALID);
        }

        return "Đăng kí tài khoản thành công.";
    }

    @Transactional
    public String verifiedCode(String email){
        if(verificationCodeRepository.existsByEmail(email)){
            verificationCodeRepository.deleteAllByEmail(email);
        }

        VerificationCode verificationCode = VerificationCode.builder()
                .email(email)
                .code(generateRandomCode())
                .expiredAt(LocalDateTime.now().plusMinutes(2))
                .build();

        verificationCodeRepository.save(verificationCode);

        StringBuilder content = new StringBuilder("Mã Xác thực đăng kí tài khoản phòng khám của bạn là: " + verificationCode.getCode() + ".\n\n");
        content.append("Mã sẽ hết hạn sau 2 phút, hãy chú ý.");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Mã xác thực đăng kí tài khoản phòng khám");
        message.setText(content.toString());

        mailSender.send(message);
        log.info("Gửi mã xác thực cho email: {} thành công.", email);

        return "Gửi mã xác thực thành công.";
    }

    private String generateRandomCode() {
        int length = 6;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10)); // từ 0-9
        }

        return sb.toString();
    }


    public AuthenticationResponse login(AuthenticationRequest request){
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> {
            log.error("Tài khoản: {} không tồn tại, đăng nhập thất bại.", request.getUsername());
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        });

        if(user.isDisable()) {
            log.error("Tài khoản: {} đã bị vô hiệu hoá, đăng nhập thất bại.", request.getUsername());
            throw new AppException(ErrorCode.USER_DISABLE);
        }

        boolean isAuthenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if(!isAuthenticated) {
            log.error("Tài khoản: {} sai mật khẩu, đăng nhập thất bại.", request.getUsername());
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String token = generateToken(user);

        return AuthenticationResponse.builder()
                .authenticated(isAuthenticated)
                .token(token)
                .build();
        }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("truong.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope",buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header,payload);

        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            log.info("Tài khoản: {} đăng nhập thành công.", user.getUsername());
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Tài khoản: {} đăng nhập thất bại, tạo token thất bại.", user.getUsername());
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if(!CollectionUtils.isEmpty(user.getRoles())){
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if(!CollectionUtils.isEmpty(role.getPermissions())){
                    role.getPermissions().forEach(permission -> {
                        stringJoiner.add(permission.getName());
                    });
                }
            });
        }

        return stringJoiner.toString();
    }
}
