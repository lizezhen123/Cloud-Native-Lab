package org.fd.ase.grp15.ase_notification_service.exception.handler;


import org.fd.ase.grp15.ase_notification_service.exception.NotificationServiceException;
import org.fd.ase.grp15.ase_notification_service.exception.code.NotificationServiceErrorCode;
import org.fd.ase.grp15.ase_notification_service.web.request.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {

    public static Map<NotificationServiceErrorCode, HttpStatus> codeToStatusMap = Map.of(
        NotificationServiceErrorCode.ERR_INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR
    );

    public ResponseEntity<ErrorResponse> handleNotificationServiceException(NotificationServiceException e) {
        var errCode = e.getCode();
        var httpStatus = codeToStatusMap.getOrDefault(errCode, HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity
                .status(httpStatus)
                .body(new ErrorResponse(e.getMessage(), errCode));
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
                .body(new ErrorResponse(ex.getMessage(), NotificationServiceErrorCode.ERR_INTERNAL_SERVER_ERROR));
    }

}
