package org.fd.ase.grp15.ase_contribute_service.service;

import org.apache.dubbo.config.annotation.DubboReference;
import org.fd.ase.grp15.ase_contribute_service.entity.Contribution;
import org.fd.ase.grp15.ase_contribute_service.entity.vo.ListContribution;
import org.fd.ase.grp15.ase_contribute_service.repository.ContributeRepository;
import org.fd.ase.grp15.ase_contribute_service.request.ContributeRequest;
import org.fd.ase.grp15.common.enums.ConferenceRole;
import org.fd.ase.grp15.common.iservice.IConferenceService;
import org.fd.ase.grp15.common.iservice.IUserConferenceRoleService;
import org.fd.ase.grp15.common.iservice.conference.dto.ConferenceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

@Service
public class ContributeServiceImpl {

    @Autowired
    private ContributeRepository contributeRepository;
    @DubboReference(check = false)
    private IUserConferenceRoleService iUserConferenceRoleService;

    @DubboReference(check = false)
    private IConferenceService conferenceService;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public String contribute(ContributeRequest.In in) {
        // 1. 调用conferenceService.getConferenceInfoByName获取会议信息(基于dubbo的rpc调用)
        ConferenceDTO conferenceDTO = conferenceService.getConferenceInfoByName(in.getConferenceName());

        // 2. 检查会议状态是否为“投稿中”，以及投稿截止时间是否已过
        if (!conferenceDTO.getConferenceStatus().equals("投稿中")) {
            return "会议当前状态不是投稿中";
        }
        if (LocalDateTime.now().isAfter(conferenceDTO.getSubmissionDeadline())) {
            return "会议投稿截止时间已过";
        }

        // 3.
        // 调用iUserConferenceRoleService.addRoleToUserInConference给用户添加author身份(基于dubbo的rpc调用)
        iUserConferenceRoleService.addRoleToUserInConference(in.getUsername(), in.getConferenceName(),
                ConferenceRole.AUTHOR);
        LocalDateTime time = LocalDateTime.now();
        Date date = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
        // 4. 创建Contribution对象并保存到数据库
        Contribution contribution = new Contribution(in.getUsername(), in.getRealName(),
                in.getConferenceName(), in.getTitle(), in.getAbstractContent(), in.getEssayId(), sdf.format(date));
        contributeRepository.save(contribution);

        return "投稿成功";
    }

    public List<ListContribution> listContributionsByUsername(String author) {
        List<ListContribution> list = new ArrayList<>();
        for (Contribution contribution : contributeRepository.findAllByAuthor(author)) {
            list.add(new ListContribution(contribution.getId(), contribution.getConferenceName(),
                    contribution.getContributeTime(), contribution.getTitle(), contribution.getStatus()));
        }
        return list;
    }

    public List<ListContribution> listContibutionsByConferenceName(String name) {
        List<ListContribution> list = new ArrayList<>();
        for (Contribution contribution : contributeRepository.findAllByConferenceName(name)) {
            list.add(new ListContribution(contribution.getId(), contribution.getConferenceName(),
                    contribution.getContributeTime(), contribution.getTitle(), contribution.getStatus()));
        }
        return list;
    }

    public Contribution detailById(String idStr) {
        long id = Long.parseLong(idStr);
        return contributeRepository.findById(id).orElse(null);
    }
}
