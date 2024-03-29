package org.fd.ase.grp15.ase_conference_service.exception.handler;

import org.fd.ase.grp15.ase_conference_service.exception.ConferenceServiceException;
import org.fd.ase.grp15.ase_conference_service.exception.code.ConferenceServiceErrorCode;
import org.fd.ase.grp15.ase_conference_service.web.bind.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {

    public static Map<ConferenceServiceErrorCode, HttpStatus> codeToStatusMap = Map.ofEntries(
            Map.entry(ConferenceServiceErrorCode.ERR_CONFERENCE_NAME_EXISTED, HttpStatus.BAD_REQUEST),
            Map.entry(ConferenceServiceErrorCode.ERR_SAME_NAME_CONFERENCE_APPLYING, HttpStatus.BAD_REQUEST),
            Map.entry(ConferenceServiceErrorCode.ERR_INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR),
            Map.entry(ConferenceServiceErrorCode.ERR_START_TIME_AFTER_END_TIME, HttpStatus.BAD_REQUEST),
            Map.entry(ConferenceServiceErrorCode.ERR_SUBMISSION_TIME_AFTER_RESULT_TIME, HttpStatus.BAD_REQUEST),
            Map.entry(ConferenceServiceErrorCode.ERR_ILLEGAL_CONFERENCE_STATUS, HttpStatus.BAD_REQUEST),
            Map.entry(ConferenceServiceErrorCode.ERR_CONFERENCE_NAME_NOT_EXISTED, HttpStatus.BAD_REQUEST),
            Map.entry(ConferenceServiceErrorCode.ERR_NOT_ADMINISTRATOR, HttpStatus.BAD_REQUEST),
            Map.entry(ConferenceServiceErrorCode.ERR_BAD_REQUEST, HttpStatus.BAD_REQUEST),
            Map.entry(ConferenceServiceErrorCode.ERR_NOT_CHAIR, HttpStatus.BAD_REQUEST),
            Map.entry(ConferenceServiceErrorCode.ERR_APPLICATION_STATUS, HttpStatus.BAD_REQUEST)
    );

    public ResponseEntity<ErrorResponse> handleUserServiceException(ConferenceServiceException e) {
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
                .body(new ErrorResponse(messageBuilder.toString(), ConferenceServiceErrorCode.ERR_BAD_REQUEST));
    }

    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(e.getMessage(), ConferenceServiceErrorCode.ERR_BAD_REQUEST));
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
                .body(new ErrorResponse(ex.getMessage(), ConferenceServiceErrorCode.ERR_INTERNAL_SERVER_ERROR));
    }

}
