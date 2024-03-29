package org.fd.ase.grp15.ase_conference_service.repository;

import org.fd.ase.grp15.ase_conference_service.entity.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConferenceRepository extends JpaRepository<Conference, String> {
    Conference findConferenceByConferenceName(String conferenceName);

}
