package org.fd.ase.grp15.ase_notification_service.exception;

import lombok.Getter;
import lombok.NonNull;
import org.fd.ase.grp15.ase_notification_service.exception.code.NotificationServiceErrorCode;


@Getter
public class NotificationServiceException extends RuntimeException {
    private final NotificationServiceErrorCode code;

    public NotificationServiceException(@NonNull NotificationServiceErrorCode code, @NonNull String message) {
        super(message);
        this.code = code;
    }
}
