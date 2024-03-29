package org.fd.ase.grp15.ase_user_service.repository;

import org.fd.ase.grp15.ase_user_service.entity.UserConferenceRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserConferenceRoleRepository extends JpaRepository<UserConferenceRole, UserConferenceRole.UserConferenceRoleId> {
    @Query("select ucr from UserConferenceRole ucr where ucr.id.username = ?1 and ucr.id.conferenceName = ?2")
    public List<UserConferenceRole> findAllByUsernameAndConferenceName(String username, String conferenceName);

    @Query("select ucr from UserConferenceRole ucr where ucr.id.username = ?1")
    public List<UserConferenceRole> findAllByUsername(String username);
}
