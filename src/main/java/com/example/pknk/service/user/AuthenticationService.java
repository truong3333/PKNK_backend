package com.example.pknk.service.user;

import com.example.pknk.domain.dto.request.user.*;
import com.example.pknk.domain.dto.response.user.AuthenticationResponse;
import com.example.pknk.domain.dto.response.user.IntrospectResponse;
import com.example.pknk.domain.dto.response.user.VerifyResetPasswordResponse;
import com.example.pknk.domain.entity.user.*;
import com.example.pknk.exception.AppException;
import com.example.pknk.exception.ErrorCode;
import com.example.pknk.repository.user.*;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
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
    RoleRepository roleRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;
    VerifyForgotPasswordRepository verifyForgotPasswordRepository;

    @Value("${jwt.signerKey}")
    @NonFinal
    protected String signerKey;

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

    @PreAuthorize("hasRole('ADMIN')")
    public String createDoctor(DoctorCreateRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            log.error("Tài khoản: {} đã tồn tại, đăng kí tài khoản bác sĩ thất bại", request.getUsername());
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        Role role = roleRepository.findById("DOCTOR").orElseThrow(() -> {
            log.error("Role: DOCTOR không tồn tại, đăng kí tài khoản bác sĩ thất bại.");
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

        Doctor doctor = Doctor.builder()
                .specialization(request.getSpecialization())
                .licenseNumber(request.getLicenseNumber())
                .yearsExperience(request.getYearsExperience())
                .build();

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .disable(false)
                .roles(roles)
                .userDetail(userDetail)
                .doctor(doctor)
                .build();

        userDetail.setUser(user);
        doctor.setUser(user);

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(request.getEmail()).orElseThrow(() -> {
            log.error("Email: {} chưa có mã xác thực đăng kí tài khoản, đăng kí tài khoản bác sĩ thất bại.", request.getEmail());
            throw new AppException(ErrorCode.VERIFIED_CODE_NOT_EXISTED);
        });

        if(request.getVerifiedCode().equals(verificationCode.getCode()) && verificationCode.getExpiredAt().isAfter(LocalDateTime.now())){
            userRepository.save(user);
            log.info("Tài khoản bác sĩ: {} đăng kí thành công.", request.getUsername());
        }else{
            log.error("Mã xác thực của email: {} không hợp lệ hoặc đã hết hạn, đăng kí tài khoản bác sĩ thất bại.", request.getEmail());
            throw new AppException(ErrorCode.VERIFIED_CODE_INVALID);
        }

        return "Đăng kí tài khoản bác sĩ thành công.";
    }

    @PreAuthorize("hasRole('ADMIN')")
    public String createNurse(NurseCreateRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            log.error("Tài khoản: {} đã tồn tại, đăng kí tài khoản y tá thất bại", request.getUsername());
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        Role role = roleRepository.findById("NURSE").orElseThrow(() -> {
            log.error("Role: NURSE không tồn tại, đăng kí tài khoản y tá thất bại.");
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

        Nurse nurse = Nurse.builder()
                .build();

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .disable(false)
                .roles(roles)
                .userDetail(userDetail)
                .nurse(nurse)
                .build();

        userDetail.setUser(user);
        nurse.setUser(user);

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(request.getEmail()).orElseThrow(() -> {
            log.error("Email: {} chưa có mã xác thực đăng kí tài khoản, đăng kí tài khoản y tá thất bại.", request.getEmail());
            throw new AppException(ErrorCode.VERIFIED_CODE_NOT_EXISTED);
        });

        if(request.getVerifiedCode().equals(verificationCode.getCode()) && verificationCode.getExpiredAt().isAfter(LocalDateTime.now())){
            userRepository.save(user);
            log.info("Tài khoản y tá: {} đăng kí thành công.", request.getUsername());
        }else{
            log.error("Mã xác thực của email: {} không hợp lệ hoặc đã hết hạn, đăng kí tài khoản y tá thất bại.", request.getEmail());
            throw new AppException(ErrorCode.VERIFIED_CODE_INVALID);
        }

        return "Đăng kí tài khoản y tá thành công.";
    }

    @Transactional
    public String verifiedCode(String email){
        if(verificationCodeRepository.existsByEmail(email)){
            verificationCodeRepository.deleteAllByEmail(email);
        }

        VerificationCode verificationCode = VerificationCode.builder()
                .email(email)
                .code(generateRandomCode())
                .expiredAt(LocalDateTime.now().plusMinutes(10))
                .build();

        verificationCodeRepository.save(verificationCode);

        StringBuilder content = new StringBuilder("Mã Xác thực đăng kí tài khoản phòng khám của bạn là: " + verificationCode.getCode() + ".\n\n");
        content.append("Mã sẽ hết hạn sau 10 phút, hãy chú ý.");

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

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        var signToken = verifyToken(request.getToken());

        String jit = signToken.getJWTClaimsSet().getJWTID();
        Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();

        invalidatedTokenRepository.save(invalidatedToken);
        log.info("Người dùng: {} đã đăng xuất.", signToken.getJWTClaimsSet().getSubject());

    }


    public IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException {
        var token = request.getToken();
        boolean isValid = true;

        try{
            verifyToken(token);
        }catch (AppException e){

            isValid = false;
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    public AuthenticationResponse refresh(RefreshTokenRequest request) throws ParseException, JOSEException {
        var refreshToken = verifyToken(request.getRefreshToken());
        
        // Verify this is a refresh token
        String tokenType = refreshToken.getJWTClaimsSet().getClaim("type") != null 
            ? refreshToken.getJWTClaimsSet().getClaim("type").toString() 
            : null;
        
        if (!"refresh".equals(tokenType)) {
            log.error("Token không phải là refresh token, refresh thất bại.");
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String username = refreshToken.getJWTClaimsSet().getSubject();
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            log.error("Tài khoản: {} không tồn tại, refresh token thất bại.", username);
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        });

        if(user.isDisable()) {
            log.error("Tài khoản: {} đã bị vô hiệu hoá, refresh token thất bại.", username);
            throw new AppException(ErrorCode.USER_DISABLE);
        }

        // Generate new tokens
        String newToken = generateToken(user);
        String newRefreshToken = generateRefreshToken(user);

        log.info("Tài khoản: {} refresh token thành công.", username);

        return AuthenticationResponse.builder()
                .authenticated(true)
                .token(newToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(signerKey.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if(!verified || expiryTime.before(new Date())){
            log.error("Token: {} không hợp lệ hoặc đã hết hạn.", token);
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if(invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())){
            log.error("Token: {} đã hết hạn do logout.", token);
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
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
            throw new AppException(ErrorCode.PASSWORD_INCORRECT);
        }

        String token = generateToken(user);
        String refreshToken = generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .authenticated(isAuthenticated)
                .token(token)
                .refreshToken(refreshToken)
                .build();
        }

    public String forgotPassword(VerifyForgotPasswordRequest request){
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> {
            log.error("Tài khoản: {} không tồn tại, không thể lấy lại mật khẩu.", request.getUsername());
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        });

        String emailCut = user.getUserDetail().getEmail().substring(0,user.getUserDetail().getEmail().indexOf('@'));
        String emailRemain = user.getUserDetail().getEmail().substring(user.getUserDetail().getEmail().indexOf('@') + 1);
        emailCut = "" + emailCut.charAt(0) + emailCut.charAt(1) + "*****" + emailCut.charAt(emailCut.length() - 2) + emailCut.charAt(emailCut.length()-1) + emailRemain;

        String idVerify = UUID.randomUUID().toString();
        VerifyForgotPassword verifyForgotPassword = VerifyForgotPassword.builder()
                .id(idVerify)
                .username(request.getUsername())
                .expiryTime(LocalDateTime.now().plusMinutes(10))
                .build();

        verifyForgotPasswordRepository.save(verifyForgotPassword);

        StringBuilder content = new StringBuilder("Tài khoản phòng khám của bạn vừa được yêu cầu đặt lại mật khẩu, vui lòng truy cập:  http://localhost:5173/reset-password/" + idVerify + "\n\n");
        content.append("Mã sẽ hết hạn sau 10 phút, hãy chú ý." + "\n");
        content.append("Nếu không phải bạn, vui lòng bỏ qua.");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getUserDetail().getEmail());
        message.setSubject("Yêu cầu đặt lại mật khẩu tài khoản phòng khám.");
        message.setText(content.toString());

        mailSender.send(message);
        log.info("Gửi yêu cầu đặt lại mật khẩu cho tài khoản: {} thành công.", request.getUsername());

        return "Yêu cầu đặt lại mật khẩu đã được gửi tới email: " + emailCut;
    }

    public VerifyResetPasswordResponse verifyResetPassword(String id){
        boolean valid = false;

        VerifyForgotPassword verifyForgotPassword = verifyForgotPasswordRepository.findById(id).orElseThrow(() -> {
            log.error("Không tồn tại yêu cầu đặt lại mật khẩu id: {} , xác thực thất bại.");
            throw new AppException(ErrorCode.VERIFY_FORGOT_PASSWORD_CODE_NOT_EXISTED);
        });

        if(verifyForgotPassword.getExpiryTime().isBefore(LocalDateTime.now())){
            valid = false;
            log.error("Yêu cầu đặt lại mật khẩu id: {} đã hết hạn, xác thực thất bại.");
            throw new AppException(ErrorCode.VERIFY_FORGOT_PASSWORD_INVALID);
        }

        valid = true;

        return VerifyResetPasswordResponse.builder()
                .id(id)
                .valid(valid)
                .build();
    }

    @Transactional
    public String resetPassword(ResetPasswordRequest request){
        VerifyForgotPassword verifyForgotPassword = verifyForgotPasswordRepository.findById(request.getId()).orElseThrow(() -> {
            log.error("Không tồn tại yêu cầu đặt lại mật khẩu id: {} , xác thực thất bại.");
            throw new AppException(ErrorCode.VERIFY_FORGOT_PASSWORD_CODE_NOT_EXISTED);
        });

        if(verifyForgotPassword.getExpiryTime().isBefore(LocalDateTime.now())){
            log.error("Yêu cầu đặt lại mật khẩu id: {} đã hết hạn, xác thực thất bại.");
            throw new AppException(ErrorCode.VERIFY_FORGOT_PASSWORD_INVALID);
        }

        User user = userRepository.findByUsername(verifyForgotPassword.getUsername()).orElseThrow(() -> {
            log.error("Tài khoản: {} không tồn tại, không thể lấy lại mật khẩu.", verifyForgotPassword.getUsername());
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        });

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        log.info("Yêu cầu đổi mật khẩu cho tài khoản: {} thành công", user.getUsername());

        if(verifyForgotPasswordRepository.existsByUsername(verifyForgotPassword.getUsername())){
            verifyForgotPasswordRepository.deleteAllByUsername(verifyForgotPassword.getUsername());
        }

        return "Hoàn tất quá trình yêu cầu đổi mật khẩu.";
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
                .jwtID(UUID.randomUUID().toString())
                .claim("scope",buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header,payload);

        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Tài khoản: {} đăng nhập thất bại, tạo token thất bại.", user.getUsername());
            throw new RuntimeException(e);
        }
    }

    private String generateRefreshToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("truong.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(7, ChronoUnit.DAYS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("type", "refresh")
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header,payload);

        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Tài khoản: {} tạo refresh token thất bại.", user.getUsername());
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
