package com.example.pknk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorCode {
    USER_NOT_EXISTED(100, "Tài khoản không tồn tại.", HttpStatus.NOT_FOUND),
    USER_EXISTED(100, "Tài khoản đã tồn tại.", HttpStatus.BAD_REQUEST),
    USER_DISABLE(100, "Người dùng đã bị vô hiệu hoá.", HttpStatus.BAD_REQUEST),
    ROLE_NOT_EXISTED(100, "Vai trò không tồn tại.", HttpStatus.NOT_FOUND),
    VERIFIED_CODE_NOT_EXISTED(100, "Email chưa có mã xác thực đăng kí tài khoản.", HttpStatus.NOT_FOUND),
    VERIFIED_CODE_INVALID(100, "Mã xác thực không hợp lệ hoặc đã hết hạn.", HttpStatus.NOT_FOUND),
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
