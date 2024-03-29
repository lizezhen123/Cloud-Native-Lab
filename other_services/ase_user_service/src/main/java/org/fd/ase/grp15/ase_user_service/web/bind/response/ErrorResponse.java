package org.fd.ase.grp15.ase_user_service.web.bind.response;


import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.fd.ase.grp15.ase_user_service.exception.code.UserServiceErrorCode;

@Getter
@Setter
public class ErrorResponse extends BaseResponse {

    private final UserServiceErrorCode code;

    public ErrorResponse(@NonNull String message, UserServiceErrorCode code) {
        super(false, message);
        this.code = code;
    }
}
