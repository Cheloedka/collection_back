package com.example.collections_backend.notifications;

import com.example.collections_backend.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Modifying
    @Query("UPDATE Notification n SET n.isRead = true WHERE n.notifiedUser = :user")
    void readNotificationsOfUser(@Param("user") User notifiedUser);

    List<Notification> getAllByNotifiedUser(User notifiedUser, Pageable pageable);

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.notifiedUser = :user AND n.isRead = false")
    int countUnreadNotificationsByUser(@Param("user") User user);

}
