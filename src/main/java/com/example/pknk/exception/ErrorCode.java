package com.example.pknk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorCode {
    USER_NOT_EXISTED(100, "Tài khoản không tồn tại.", HttpStatus.NOT_FOUND),
    USER_EXISTED(100, "Tài khoản đã tồn tại.", HttpStatus.BAD_REQUEST),
    USER_DISABLE(100, "Người dùng đã bị vô hiệu hoá.", HttpStatus.BAD_REQUEST),
    VERIFIED_CODE_NOT_EXISTED(100, "Email chưa có mã xác thực đăng kí tài khoản.", HttpStatus.NOT_FOUND),
    VERIFIED_CODE_INVALID(100, "Mã xác thực đăng kí tài khoản không hợp lệ hoặc đã hết hạn.", HttpStatus.BAD_REQUEST),
    VERIFY_FORGOT_PASSWORD_CODE_NOT_EXISTED(100, "Mã xác thực yêu cầu đổi mật khẩu không tồn tại.", HttpStatus.NOT_FOUND),
    VERIFY_FORGOT_PASSWORD_INVALID(100, "Mã xác thực yêu cầu đổi mật khẩu không hợp lệ hoặc đã hết hạn.", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(100, "Tài khoản chưa được xác thực, xác thực thất bại", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(100,"Tài khoản không đủ quyền để truy cập", HttpStatus.UNAUTHORIZED),
    INVALID_KEY(100,"Lỗi dữ liệu đầu vào", HttpStatus.BAD_REQUEST),
    ROLE_EXISTED(100,"Role đã tồn tại", HttpStatus.BAD_REQUEST),
    ROLE_NOT_EXISTED(100,"Role không tồn tại", HttpStatus.NOT_FOUND),
    PERMISSION_EXISTED(100,"Permission đã tồn tại", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_EXISTED(100,"Permission không tồn tại", HttpStatus.NOT_FOUND),
    PASSWORD_INCORRECT(100,"Mật khẩu không chính xác.", HttpStatus.BAD_REQUEST),
    OLD_PASSWORD_INCORRECT(100,"Mật khẩu cũ không chính xác.", HttpStatus.BAD_REQUEST),


    PATIENT_NOT_EXISTED(100,"Bệnh nhân không tồn tại", HttpStatus.BAD_REQUEST),


    DOCTOR_NOT_EXISTED(100,"Bác sĩ không tồn tại", HttpStatus.BAD_REQUEST),

    APPOINTMENT_NOT_EXISTED(100,"Lịch hẹn không tồn tại", HttpStatus.BAD_REQUEST),
    APPOINTMENT_EXISTED(100,"Lịch hẹn đã tồn tại", HttpStatus.BAD_REQUEST),

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
