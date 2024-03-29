package org.fd.ase.grp15.ase_notification_service.service;


import org.fd.ase.grp15.ase_notification_service.entity.Notification;
import org.fd.ase.grp15.ase_notification_service.web.request.params.HandleInvitationParams;
import org.fd.ase.grp15.ase_notification_service.web.request.params.InvitePCMParams;

import java.util.List;

public interface IPCMInvitationService {
    /**
     *
     * @param params
     */
    public String invitePCM(InvitePCMParams params);

    /**
     *
     * @param username
     * @return
     */
    public List<Notification> searchSentInvitations(String username);

    /**
     *
     * @param username
     * @return
     */
    public List<Notification> searchReceivedInvitation(String username);

    /**
     *
     * @param params
     */
    public String handleInvitation(HandleInvitationParams params);

    /**
     *
     * @param conferenceName
     * @return
     */
    public List<Notification> searchConferenceInvitations(String conferenceName);
}
