package org.fd.ase.grp15.ase_conference_service.web.bind.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class BaseResponse {
    @NonNull
    private Long timestamp;
    @NonNull
    private Boolean success;
    @NonNull
    private String message;

    public BaseResponse(@NonNull Boolean success, @NonNull String message) {
        this.timestamp = System.currentTimeMillis();
        this.success = success;
        this.message = message;
    }
}
