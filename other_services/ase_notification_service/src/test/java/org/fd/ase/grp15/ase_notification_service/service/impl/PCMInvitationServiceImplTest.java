package org.fd.ase.grp15.ase_notification_service.service.impl;

import org.fd.ase.grp15.ase_notification_service.entity.Notification;
import org.fd.ase.grp15.ase_notification_service.exception.NotificationServiceException;
import org.fd.ase.grp15.ase_notification_service.exception.code.NotificationServiceErrorCode;
import org.fd.ase.grp15.ase_notification_service.repository.NotificationRepository;
import org.fd.ase.grp15.ase_notification_service.service.IPCMInvitationService;
import org.fd.ase.grp15.ase_notification_service.web.request.params.HandleInvitationParams;
import org.fd.ase.grp15.ase_notification_service.web.request.params.InvitePCMParams;
import org.fd.ase.grp15.common.enums.ConferenceRole;
import org.fd.ase.grp15.common.iservice.IUserConferenceRoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
class PCMInvitationServiceImplTest {

    @Autowired
    IPCMInvitationService inviteService;

    @Autowired
    NotificationRepository notificationRepository;

    @Mock
    private IUserConferenceRoleService userConferenceRoleService;

    @BeforeEach
    public void before() {
        notificationRepository.deleteAll();

        // 把inviteService里的IUserConferenceRoleService替换成mock
        Field field = ReflectionUtils.findField(PCMInvitationServiceImpl.class, "userConferenceRoleService");
        assert field != null;
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, inviteService, userConferenceRoleService);
    }

    @Test
    void invitePCM() {
        assertEquals(0, notificationRepository.findAll().size());

        String sender = "cx1";
        String conferenceName = "conferenceTest1";

        InvitePCMParams params1 = new InvitePCMParams(sender, conferenceName, new ArrayList<String>(List.of("cx2")));
        when(userConferenceRoleService.checkRoleOfUserInConference(sender, conferenceName, ConferenceRole.CHAIR))
                .thenReturn(false)
                .thenReturn(true); // 第一次返回false，第二次返回true，分别测试无权限和有权限的情况

        var ex = assertThrows(NotificationServiceException.class, () -> inviteService.invitePCM(params1));
        assertEquals(ex.getCode(), NotificationServiceErrorCode.ERR_FORBIDDEN);

        inviteService.invitePCM(params1);

        assertEquals(1, notificationRepository.findAll().size());
        Notification notification = notificationRepository.findNotificationsBySender("cx1").get(0);
        assertEquals("conferenceTest1", notification.getConferenceName());
        assertEquals("cx2", notification.getReceiver());

        // 验证checkRoleOfUserInConference方法被调用了两次
        verify(userConferenceRoleService, times(2)).checkRoleOfUserInConference(sender, conferenceName, ConferenceRole.CHAIR);

    }

    @Test
    void searchSentInvitations() {
        assertEquals(0, notificationRepository.findNotificationsBySender("cx1").size());

        when(userConferenceRoleService.checkRoleOfUserInConference("cx1", "conferenceTest1", ConferenceRole.CHAIR))
                .thenReturn(true); // 返回true，表示cx1是conferenceTest1的chair

        InvitePCMParams params1 = new InvitePCMParams("cx1", "conferenceTest1", new ArrayList<String>(List.of("cx2", "cx3")));
        inviteService.invitePCM(params1);

        assertEquals(2, notificationRepository.findNotificationsBySender("cx1").size());
        List<Notification> notificationList = notificationRepository.findNotificationsBySender("cx1");

        List<String> receivers = new ArrayList<>();
        for (Notification notification : notificationList) {
            receivers.add(notification.getReceiver());
        }

        assertTrue(receivers.contains("cx2"));
    }

    @Test
    void searchReceivedInvitation() {
        assertEquals(0, notificationRepository.findNotificationsByReceiver("cx3").size());

        when(userConferenceRoleService.checkRoleOfUserInConference("cx1", "conferenceTest1", ConferenceRole.CHAIR))
                .thenReturn(true); // 返回true，表示cx1是conferenceTest1的chair

        InvitePCMParams params1 = new InvitePCMParams("cx1", "conferenceTest1", new ArrayList<String>(List.of("cx3")));

        inviteService.invitePCM(params1);
        assertEquals(1, notificationRepository.findNotificationsByReceiver("cx3").size());
        List<Notification> notificationList = notificationRepository.findNotificationsByReceiver("cx3");
        assertEquals("cx1", notificationList.get(0).getSender());
    }

    @Test
    void handleInvitation() {
        InvitePCMParams params1 = new InvitePCMParams("cx1", "conferenceTest1", new ArrayList<String>(List.of("cx2")));
        InvitePCMParams params2 = new InvitePCMParams("cx3", "conferenceTest2", new ArrayList<String>(List.of("cx4")));

        when(userConferenceRoleService.checkRoleOfUserInConference("cx1", "conferenceTest1", ConferenceRole.CHAIR))
                .thenReturn(true); // 返回true，表示cx1是conferenceTest1的chair
        when(userConferenceRoleService.checkRoleOfUserInConference("cx3", "conferenceTest2", ConferenceRole.CHAIR))
                .thenReturn(true); // 返回true，表示cx3是conferenceTest2的chair

        inviteService.invitePCM(params1);
        inviteService.invitePCM(params2);

        Notification notification1 = notificationRepository.findNotificationsByReceiver("cx2").get(0);
        Notification notification2 = notificationRepository.findNotificationsByReceiver("cx4").get(0);

        assertEquals(0, notification1.getStatus());
        assertEquals(0, notification2.getStatus());

        HandleInvitationParams handleInvitationParams1 = new HandleInvitationParams("cx2", notification1.getNotificationId(), true);
        HandleInvitationParams handleInvitationParams2 = new HandleInvitationParams("cx4", notification2.getNotificationId(), false);

        inviteService.handleInvitation(handleInvitationParams1);
        inviteService.handleInvitation(handleInvitationParams2);

        notification1 = notificationRepository.findNotificationsByReceiver("cx2").get(0);
        notification2 = notificationRepository.findNotificationsByReceiver("cx4").get(0);
        assertEquals(1, notification1.getStatus());
        assertEquals(2, notification2.getStatus());
    }

    @Test
    void searchConferenceInvitations() {
        assertEquals(0, inviteService.searchConferenceInvitations("conferenceTest1").size());

        when(userConferenceRoleService.checkRoleOfUserInConference("cx1", "conferenceTest1", ConferenceRole.CHAIR))
                .thenReturn(true); // 返回true，表示cx1是conferenceTest1的chair

        InvitePCMParams params1 = new InvitePCMParams("cx1", "conferenceTest1", new ArrayList<String>(List.of("cx2", "cx3", "cx4")));
        inviteService.invitePCM(params1);
        assertEquals(3, inviteService.searchConferenceInvitations("conferenceTest1").size());
        assertEquals("cx1", inviteService.searchConferenceInvitations("conferenceTest1").get(0).getSender());
    }
}