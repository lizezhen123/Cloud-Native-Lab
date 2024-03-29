package org.fd.ase.grp15.ase_user_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User extends AuditModel {
    @Id
    private String username; // 用户名

    @Column(name = "real_name")
    private String realName; // 用户真实姓名

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "institution_name")
    private String institutionName;

    @Column(name = "area")
    private String area;
}
