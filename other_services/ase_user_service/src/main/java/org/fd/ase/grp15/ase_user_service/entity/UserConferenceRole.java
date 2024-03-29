package org.fd.ase.grp15.ase_user_service.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Table(name = "user_conference_role")
@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class UserConferenceRole {

    @Embeddable
    @Data
    @RequiredArgsConstructor
    @NoArgsConstructor
    public static class UserConferenceRoleId implements Serializable {
        @NonNull
        private String username;
        @NonNull
        private String conferenceName;
        @NonNull
        private String role;
    }

    @EmbeddedId
    @NonNull
    private UserConferenceRoleId id;
}
