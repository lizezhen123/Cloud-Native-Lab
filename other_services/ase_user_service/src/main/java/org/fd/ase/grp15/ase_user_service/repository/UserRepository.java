package org.fd.ase.grp15.ase_user_service.repository;

import org.fd.ase.grp15.ase_user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);

    List<User> findUsersByRealName(String realName);
}
