package com.example.pknk.configuration;

import com.example.pknk.domain.dto.request.user.IntrospectRequest;
import com.example.pknk.service.user.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Objects;

@Component
public class CustomJwtDecoder implements JwtDecoder {

    @Value("${jwt.signerKey}")
    private String signerKey;

    @Autowired
    @Lazy
    private AuthenticationService authenticationService;

    private NimbusJwtDecoder nimbusJwtDecoder = null;

    private static final String[] PUBLIC_ENDPOINTS = {
            "/api/v1/auth/verifiedCode",
            "/api/v1/auth/login",
            "/api/v1/auth/register/patient",
            "/api/v1/auth/logout",
            "/api/v1/auth/introspect",
            "/api/v1/auth/forgotPassword",
            "/api/v1/auth/verifyResetPassword",
            "/api/v1/auth/resetPassword",
    };

    private boolean isPublicEndpoint(String path) {
        if (path == null) return false;
        return Arrays.stream(PUBLIC_ENDPOINTS).anyMatch(endpoint -> {
            if (endpoint.contains("**")) {
                String pattern = endpoint.replace("**", ".*");
                return path.matches(pattern);
            }
            return path.startsWith(endpoint);
        });
    }

    @Override
    public Jwt decode(String token){
        // For public endpoints, skip JWT validation if token is invalid/expired
        // The controller will handle authentication from request body if needed
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                String path = request.getRequestURI();
                String method = request.getMethod();
                
                // For public POST endpoints, allow invalid/expired tokens to pass through
                // The controller will validate token from request body instead
                if ("POST".equals(method) && isPublicEndpoint(path)) {
                    // Skip introspect validation for public endpoints
                    // Just decode the JWT structure without validating expiration
                    if(Objects.isNull(nimbusJwtDecoder)){
                        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
                        nimbusJwtDecoder = NimbusJwtDecoder
                                .withSecretKey(secretKeySpec)
                                .macAlgorithm(MacAlgorithm.HS512)
                                .build();
                    }
                    
                    try {
                        // Try to decode without validation
                        return nimbusJwtDecoder.decode(token);
                    } catch (JwtException e) {
                        // If decode fails, throw exception but allow it to be handled by accessDeniedHandler
                        throw new JwtException("Token không hơp lệ hoặc đã hết hạn");
                    }
                }
            }
        } catch (Exception e) {
            // If we can't determine the endpoint, proceed with normal validation
        }

        // For protected endpoints, validate token normally
        try{
            var response = authenticationService.introspect(IntrospectRequest.builder()
                            .token(token)
                    .build());

            if(!response.isValid())
                throw new JwtException("Token không hơp lệ hoặc đã hết hạn");
        } catch (JOSEException | ParseException e) {
            throw new JwtException(e.getMessage());
        }

        if(Objects.isNull(nimbusJwtDecoder)){
            SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder
                    .withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();

        }

        return nimbusJwtDecoder.decode(token);
    }
}
