package org.fd.ase.grp15.ase_conference_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "conference")
@Getter
@Setter
public class Conference extends AuditModel {
    @Id
    private String conferenceName; // 会议全称，主键

    @Column(name = "conferenceAbbr")
    private String conferenceAbbr; // 会议简称

    @Column(name = "conference StartAt")
    private LocalDateTime conferenceStartAt;    // 会议举办时间-左端点

    @Column(name = "conference EndAt")
    private LocalDateTime conferenceEndAt;      // 会议举办时间-右端点

    @Column(name = "venue")
    private String venue;          // 会议举办地点

    @Column(name = "submissionDeadline")
    private LocalDateTime submissionDeadline;   // 会议投稿截止日

    @Column(name = "reviewResult AnnounceAt")
    private LocalDateTime reviewResultAnnounceAt;      // 会议评审结果发布日期

    @Column(name = "conferenceStatus")
    private String conferenceStatus;          // 会议状态(准备中、投稿中、审稿中、终评中、审稿结束)

}
