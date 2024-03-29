package org.fd.ase.grp15.ase_notification_service.service.impl;

import org.apache.dubbo.config.annotation.DubboReference;
import org.fd.ase.grp15.ase_notification_service.entity.Notification;
import org.fd.ase.grp15.ase_notification_service.exception.NotificationServiceException;
import org.fd.ase.grp15.ase_notification_service.exception.code.NotificationServiceErrorCode;
import org.fd.ase.grp15.ase_notification_service.repository.NotificationRepository;
import org.fd.ase.grp15.ase_notification_service.service.IPCMInvitationService;
import org.fd.ase.grp15.ase_notification_service.web.request.params.HandleInvitationParams;
import org.fd.ase.grp15.ase_notification_service.web.request.params.InvitePCMParams;
import org.fd.ase.grp15.common.iservice.IUserConferenceRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.fd.ase.grp15.common.enums.ConferenceRole.CHAIR;
import static org.fd.ase.grp15.common.enums.ConferenceRole.PC_MEMBER;

@Service
public class PCMInvitationServiceImpl implements IPCMInvitationService {


    @Autowired
    NotificationRepository notificationRepository;

    @DubboReference(mock = "org.fd.ase.grp15.ase_notification_service.service.mock.UserConferenceRoleServiceMock", check = false)
    IUserConferenceRoleService userConferenceRoleService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public String invitePCM(InvitePCMParams params) {
        Date date = new Date();
        String strDate = sdf.format(date);

        // 检查sender是否是该会议的chair
        if (!userConferenceRoleService.checkRoleOfUserInConference(params.getSender(), params.getConferenceName(), CHAIR)) {
            throw new NotificationServiceException(NotificationServiceErrorCode.ERR_FORBIDDEN, "只有chair才能邀请PCMember");
        }

        for (String receiver : params.getReceiverList()) {
            Notification notification = new Notification();
            notification.setSender(params.getSender());
            notification.setConferenceName(params.getConferenceName());
            notification.setReceiver(receiver);
            notification.setStatus(0);
            notification.setInviteTime(strDate);
            notificationRepository.save(notification);
        }
        return "邀请成功";
    }

    @Override
    public List<Notification> searchSentInvitations(String username) {
        return notificationRepository.findNotificationsBySender(username);
    }

    @Override
    public List<Notification> searchReceivedInvitation(String username) {
        return notificationRepository.findNotificationsByReceiver(username);
    }

    @Override
    public String handleInvitation(HandleInvitationParams params) {
        Notification notification = notificationRepository.findNotificationByNotificationId(params.getNotificationId());
        if(params.isAccept()){
            userConferenceRoleService.addRoleToUserInConference(params.getUsername(), notification.getConferenceName(),PC_MEMBER);
        }

        Date date = new Date();
        String strDate = sdf.format(date);
        notification.setStatus(params.isAccept()?1:2);
        notification.setHandleTime(strDate);
        notificationRepository.save(notification);
        return "操作成功";
    }

    @Override
    public List<Notification> searchConferenceInvitations(String conferenceName) {
        return notificationRepository.findNotificationsByConferenceName(conferenceName);
    }


}
