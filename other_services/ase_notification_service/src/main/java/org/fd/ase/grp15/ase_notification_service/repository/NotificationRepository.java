package org.fd.ase.grp15.ase_notification_service.repository;

import org.fd.ase.grp15.ase_notification_service.entity.Notification;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {
    Notification findNotificationByNotificationId(String id);

    List<Notification> findNotificationsBySender(String sender);

    List<Notification> findNotificationsByReceiver(String sender);

    List<Notification> findNotificationsByConferenceName(String sender);
}
