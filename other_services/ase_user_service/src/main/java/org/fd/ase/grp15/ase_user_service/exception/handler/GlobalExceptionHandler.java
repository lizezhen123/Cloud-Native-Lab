package org.fd.ase.grp15.ase_user_service.exception.handler;

import org.fd.ase.grp15.ase_user_service.exception.UserServiceException;
import org.fd.ase.grp15.ase_user_service.exception.code.UserServiceErrorCode;
import org.fd.ase.grp15.ase_user_service.web.bind.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {

    public static Map<UserServiceErrorCode, HttpStatus> codeToStatusMap = Map.of(
            UserServiceErrorCode.ERR_PASSWORD_CONTAINS_USERNAME, HttpStatus.BAD_REQUEST,
            UserServiceErrorCode.ERR_USERNAME_EXISTED, HttpStatus.BAD_REQUEST,
            UserServiceErrorCode.ERR_INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR,
            UserServiceErrorCode.ERR_BAD_REQUEST, HttpStatus.BAD_REQUEST
    );

    public ResponseEntity<ErrorResponse> handleUserServiceException(UserServiceException e) {
        var errCode = e.getCode();
        var httpStatus = codeToStatusMap.getOrDefault(errCode, HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity
                .status(httpStatus)
                .body(new ErrorResponse(e.getMessage(), errCode));
    }

    public ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        StringBuilder messageBuilder = new StringBuilder();
        e.getFieldErrors().forEach(fieldError -> {
            messageBuilder.append(fieldError.getField())
                    .append(": ")
                    .append(fieldError.getDefaultMessage())
                    .append("; ");
        });
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(messageBuilder.toString(), UserServiceErrorCode.ERR_BAD_REQUEST));
    }

    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(e.getMessage(), UserServiceErrorCode.ERR_BAD_REQUEST));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception ex) {
        Class<?> currentExceptionClazz = ex.getClass();
        while (currentExceptionClazz != null) {
            try {
                var handler = this.getClass().getDeclaredMethod("handle" + currentExceptionClazz.getSimpleName(), currentExceptionClazz);
                var rsp = handler.invoke(this, ex);
                return (ResponseEntity<ErrorResponse>) rsp;
            } catch (Exception e) {
                System.out.println(e);
            }
            currentExceptionClazz = currentExceptionClazz.getSuperclass();
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(ex.getMessage(), UserServiceErrorCode.ERR_INTERNAL_SERVER_ERROR));
    }

}
