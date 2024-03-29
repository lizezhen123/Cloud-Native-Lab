package org.fd.ase.grp15.ase_notification_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String notificationId;

    @Column(name = "sender")
    private String sender; // 邀请者用户名

    @Column(name = "receiver")
    private String receiver; // 接收者用户名

    @Column(name = "conferenceName")
    private String conferenceName; // 会议全称


    @Column(name = "status")
    private int status; // 邀请状态，0-待确认，1已同意，2已拒绝

    @Column(name = "inviteTime")
    private String inviteTime;

    @Column(name = "handleTime")
    private String handleTime;


}
