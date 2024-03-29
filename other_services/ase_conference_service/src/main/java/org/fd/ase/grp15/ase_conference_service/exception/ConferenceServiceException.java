package org.fd.ase.grp15.ase_conference_service.exception;

import lombok.Getter;
import lombok.NonNull;
import org.fd.ase.grp15.ase_conference_service.exception.code.ConferenceServiceErrorCode;

@Getter
public class ConferenceServiceException extends RuntimeException {
    private final ConferenceServiceErrorCode code;

    public ConferenceServiceException(@NonNull ConferenceServiceErrorCode code, @NonNull String message) {
        super(message);
        this.code = code;
    }
}
