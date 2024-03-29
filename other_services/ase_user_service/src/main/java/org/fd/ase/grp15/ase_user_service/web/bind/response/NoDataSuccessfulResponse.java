package org.fd.ase.grp15.ase_user_service.web.bind.response;

import lombok.NonNull;

public class NoDataSuccessfulResponse extends BaseResponse {
    public NoDataSuccessfulResponse(@NonNull String message) {
        super(true, message);
    }
}
