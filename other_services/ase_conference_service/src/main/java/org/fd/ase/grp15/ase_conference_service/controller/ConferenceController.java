package org.fd.ase.grp15.ase_conference_service.controller;

import org.fd.ase.grp15.ase_conference_service.service.impl.ConferenceServiceImpl;
import org.fd.ase.grp15.ase_conference_service.web.bind.response.NoDataSuccessfulResponse;
import org.fd.ase.grp15.ase_conference_service.web.bind.response.WithDataSuccessfulResponse;
import org.fd.ase.grp15.common.iservice.conference.dto.ConferenceApplicationDTO;
import org.fd.ase.grp15.common.iservice.conference.dto.ConferenceDTO;
import org.fd.ase.grp15.common.iservice.conference.param.ApplyConferenceParam;
import org.fd.ase.grp15.common.iservice.conference.param.AuditConferenceParam;
import org.fd.ase.grp15.common.iservice.conference.param.StartSubmissionParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conference")
public class ConferenceController {

    @Autowired
    private ConferenceServiceImpl conferenceService;

    @PostMapping("/apply")
    public NoDataSuccessfulResponse apply(@RequestBody @Validated ApplyConferenceParam.In in) {
        String msg = conferenceService.apply(in);
        return new NoDataSuccessfulResponse(msg);
    }

    @GetMapping("/getAllConferences")
    public WithDataSuccessfulResponse<List<ConferenceDTO>> getAllConferences() {
        List<ConferenceDTO> data = conferenceService.getAllConferences();
        return new WithDataSuccessfulResponse<>("获取所有会议信息成功", data);
    }

    @GetMapping("/getAllMyAppliedConference")
    public WithDataSuccessfulResponse<List<ConferenceApplicationDTO>> getAllMyAppliedConference() {
        List<ConferenceApplicationDTO> data = conferenceService.getAllMyAppliedConference();
        return new WithDataSuccessfulResponse<>("获取我申请的所有会议信息成功", data);
    }

    @GetMapping("/getAllMyJoinedConference")
    public WithDataSuccessfulResponse<List<ConferenceDTO>> getAllMyJoinedConference() {
        List<ConferenceDTO> data = conferenceService.getAllMyJoinedConference();
        return new WithDataSuccessfulResponse<>("获取我参加的所有会议信息成功", data);
    }

    @GetMapping("/getMyRoleInConference/{conferenceName}")
    public WithDataSuccessfulResponse<List<String>> getMyRoleInConference(@PathVariable @Validated String conferenceName) {
        List<String> data = conferenceService.getMyRoleInConference(conferenceName);
        return new WithDataSuccessfulResponse<>("获取我在该会议中的角色成功", data);
    }

    @GetMapping("/getConferenceInfoByName/{conferenceName}")
    public WithDataSuccessfulResponse<ConferenceDTO> getConferenceInfoByName(@PathVariable @Validated String conferenceName) {
        ConferenceDTO data = conferenceService.getConferenceInfoByName(conferenceName);
        return new WithDataSuccessfulResponse<>("查看会议详细信息成功", data);
    }


    @PostMapping("/startSubmission")
    public NoDataSuccessfulResponse startSubmission(@RequestBody @Validated StartSubmissionParam.In in) {
        String msg = conferenceService.startSubmission(in);
        return new NoDataSuccessfulResponse(msg);
    }

    @GetMapping("/getAllConferenceApplications")
    public WithDataSuccessfulResponse<List<ConferenceApplicationDTO>> getAllConferenceApplications() {
        List<ConferenceApplicationDTO> data = conferenceService.getAllConferenceApplications();
        return new WithDataSuccessfulResponse<>("查看所有会议申请成功", data);
    }

    @PostMapping("/auditConference")
    public NoDataSuccessfulResponse auditConference(@RequestBody @Validated AuditConferenceParam.In in) {
        String msg = conferenceService.auditConference(in);
        return new NoDataSuccessfulResponse(msg);
    }

}
