package org.fd.ase.grp15.ase_conference_service.repository;

import org.fd.ase.grp15.ase_conference_service.entity.ConferenceApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import java.util.Optional;
import java.util.List;

@Repository
public interface ConferenceApplicationRepository extends JpaRepository<ConferenceApplication, Integer> {
    // 查询所有与指定会议名称相匹配的 ConferenceApplication
    List<ConferenceApplication> findAllByConferenceName(String conferenceName);

    List<ConferenceApplication> findAllByApplicantUsername(String applicantUsername);

    ConferenceApplication findConferenceApplicationByApplicationID(Integer applicationId);

    // 查询数据库中当前最大的 id 值
    @Query(value = "SELECT MAX(applicationID) FROM ConferenceApplication")
    Optional<Integer> findMaxId();
}