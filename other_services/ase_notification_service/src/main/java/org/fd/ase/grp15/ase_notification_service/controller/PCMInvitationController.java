package org.fd.ase.grp15.ase_notification_service.controller;

import org.fd.ase.grp15.ase_notification_service.entity.Notification;
import org.fd.ase.grp15.ase_notification_service.service.IPCMInvitationService;
import org.fd.ase.grp15.ase_notification_service.service.impl.PCMInvitationServiceImpl;
import org.fd.ase.grp15.ase_notification_service.web.request.params.HandleInvitationParams;
import org.fd.ase.grp15.ase_notification_service.web.request.params.InvitePCMParams;
import org.fd.ase.grp15.ase_notification_service.web.request.response.NoDataSuccessfulResponse;
import org.fd.ase.grp15.ase_notification_service.web.request.response.WithDataSuccessfulResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class PCMInvitationController {
    @Autowired
    IPCMInvitationService inviteService;

    @GetMapping()
    public String test(){
        return "hello";
    }

    @PostMapping("/invitePCMember")
    public NoDataSuccessfulResponse invitePCM(@RequestBody InvitePCMParams params){
        String res = inviteService.invitePCM(params);
        return new NoDataSuccessfulResponse(res);
    }

    @GetMapping("/search/allSentInvitation/{username}")
    public WithDataSuccessfulResponse searchAllSentInvitation(@PathVariable String username){
        List<Notification> res = inviteService.searchSentInvitations(username);
        return new WithDataSuccessfulResponse("查询成功",res);
    }

    @GetMapping("/search/allReceivedInvitation/{username}")
    public WithDataSuccessfulResponse searchAllReceivedInvitation(@PathVariable String username){
        List<Notification> res = inviteService.searchReceivedInvitation(username);
        return new WithDataSuccessfulResponse("查询成功",res);
    }


    @PostMapping("/handleInvitation")
    public NoDataSuccessfulResponse handleInvitation(@RequestBody HandleInvitationParams params){
        String res = inviteService.handleInvitation(params);
        return new NoDataSuccessfulResponse(res);
    }

    @GetMapping("/search/conferenceInvitations/{conferenceName}")
    public WithDataSuccessfulResponse searchConferenceInvitations(@PathVariable String conferenceName){
        List<Notification> res = inviteService.searchConferenceInvitations(conferenceName);
        return new WithDataSuccessfulResponse("查询成功",res);
    }




}
