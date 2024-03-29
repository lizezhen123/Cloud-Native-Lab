package org.fd.ase.grp15.ase_conference_service.web.bind.response;


import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.fd.ase.grp15.ase_conference_service.exception.code.ConferenceServiceErrorCode;

@Getter
@Setter
public class ErrorResponse extends BaseResponse {

    private final ConferenceServiceErrorCode code;

    public ErrorResponse(@NonNull String message, ConferenceServiceErrorCode code) {
        super(false, message);
        this.code = code;
    }
}
