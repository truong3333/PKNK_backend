package com.example.pknk.exception;

import com.example.pknk.domain.dto.response.user.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value =  RuntimeException.class)
    ResponseEntity<ApiResponses> handlingRuntimeException(RuntimeException exception){
        ApiResponses apiResponses = ApiResponses.builder()
                .code(1001)
                .result(exception.getMessage())
                .build();

        return ResponseEntity.badRequest().body(apiResponses);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponses> handlingAppException(AppException exception){
        ApiResponses apiResponses = ApiResponses.builder()
                .code(exception.getErrorCode().getCode())
                .result(exception.getErrorCode().getMessage())
                .build();

        return ResponseEntity.status(exception.getErrorCode().getStatusCode()).body(apiResponses);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponses> handlingAccessDeniedException(AccessDeniedException exception){
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        ApiResponses apiResponses = ApiResponses.builder()
                .code(errorCode.getCode())
                .result(errorCode.getMessage())
                .build();

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponses);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponses> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        String enumKey = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;

        errorCode = ErrorCode.valueOf(enumKey);

        ApiResponses apiResponses = ApiResponses.builder()
                .code(errorCode.getCode())
                .result(errorCode.getMessage())
                .build();

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponses);
    }
}
