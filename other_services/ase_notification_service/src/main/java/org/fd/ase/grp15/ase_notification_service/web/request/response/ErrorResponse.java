package org.fd.ase.grp15.ase_notification_service.web.request.response;


import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.fd.ase.grp15.ase_notification_service.exception.code.NotificationServiceErrorCode;


@Getter
@Setter
public class ErrorResponse extends BaseResponse {

    private final NotificationServiceErrorCode code;

    public ErrorResponse(@NonNull String message, NotificationServiceErrorCode code) {
        super(false, message);
        this.code = code;
    }
}
