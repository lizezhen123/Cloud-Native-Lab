package org.fd.ase.grp15.ase_notification_service.web.request.response;


import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class WithDataSuccessfulResponse<T> extends BaseResponse {

    private T data;

    public WithDataSuccessfulResponse(@NonNull String message,
                                      T data) {
        super(true, message);
        this.data = data;
    }
}
