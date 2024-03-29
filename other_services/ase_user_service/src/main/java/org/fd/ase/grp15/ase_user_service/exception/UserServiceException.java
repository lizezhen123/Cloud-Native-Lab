package org.fd.ase.grp15.ase_user_service.exception;

import lombok.Getter;
import lombok.NonNull;
import org.fd.ase.grp15.ase_user_service.exception.code.UserServiceErrorCode;

@Getter
public class UserServiceException extends RuntimeException {
    private final UserServiceErrorCode code;

    public UserServiceException(@NonNull UserServiceErrorCode code, @NonNull String message) {
        super(message);
        this.code = code;
    }
}
