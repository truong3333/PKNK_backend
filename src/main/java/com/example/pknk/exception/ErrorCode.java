package com.example.pknk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorCode {
    USER_NOT_EXISTED(100, "Người dùng không tồn tại.", HttpStatus.NOT_FOUND),
    USER_DISABLE(100, "Người dùng đã bị vô hiệu hoá.", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(100, "Tài khoản chưa được xác thực, xác thực thất bại", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(100,"Tài khoản không đủ quyền để truy cập", HttpStatus.UNAUTHORIZED),
    INVALID_KEY(100,"Lỗi dữ liệu đầu vào", HttpStatus.BAD_REQUEST),
    ;


    private int code;
    private String message;
    private HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode){
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }
}
