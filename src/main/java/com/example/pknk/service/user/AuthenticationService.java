package com.example.pknk.service.user;

import com.example.pknk.domain.dto.request.user.AuthenticationRequest;
import com.example.pknk.domain.dto.response.user.AuthenticationResponse;
import com.example.pknk.domain.entity.user.User;
import com.example.pknk.exception.AppException;
import com.example.pknk.exception.ErrorCode;
import com.example.pknk.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class AuthenticationService {
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;

    @Value("${jwt.signerKey")
    @NonFinal
    String signerKey;

    public AuthenticationResponse login(AuthenticationRequest request){
        User user = userRepository.findByUserName(request.getUsername()).orElseThrow(() -> {
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
