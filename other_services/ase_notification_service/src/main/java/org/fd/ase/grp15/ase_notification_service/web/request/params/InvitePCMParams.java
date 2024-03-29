package org.fd.ase.grp15.ase_notification_service.web.request.params;

import lombok.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvitePCMParams {
    private String sender;

    private String conferenceName;

    private List<String> receiverList;
}

