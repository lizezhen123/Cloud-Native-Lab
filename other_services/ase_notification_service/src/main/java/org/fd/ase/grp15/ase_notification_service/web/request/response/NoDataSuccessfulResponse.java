package org.fd.ase.grp15.ase_notification_service.web.request.response;

import lombok.NonNull;

public class NoDataSuccessfulResponse extends BaseResponse {
    public NoDataSuccessfulResponse(@NonNull String message) {
        super(true, message);
    }
}
