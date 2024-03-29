package org.fd.ase.grp15.ase_conference_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "conference_application")
@Getter
@Setter
public class ConferenceApplication extends AuditModel {
    @Id
    @Column(name = "applicationID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer applicationID;     // 申请ID，主键

    @Column(name = "conferenceName")
    private String conferenceName; // 会议全称

    @Column(name = "conferenceAbbr")
    private String conferenceAbbr; // 会议简称

    @Column(name = "conference StartAt")
    private LocalDateTime conferenceStartAt;    // 会议举办时间-左端点

    @Column(name = "conference EndAt")
    private LocalDateTime conferenceEndAt;      // 会议举办时间-右端点

    @Column(name = "venue")
    private String venue;          // 会议举办地点

    @Column(name = "applicationStatus")
    private String applicationStatus;          // 申请状态（审核中，已通过，未通过）

    @Column(name = "applicantUsername")
    private String applicantUsername;          // 申请人用户名
}
