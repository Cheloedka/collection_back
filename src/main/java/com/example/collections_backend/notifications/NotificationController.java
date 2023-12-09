package com.example.collections_backend.notifications;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/get")
    public List<NotificationDto> getNotifications(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "15", required = false) int size,
            @RequestParam(defaultValue = "false", required = false) boolean isRead
    ) {

        if (isRead)
            notificationService.readNotificationsOfCurrentUser();

        return notificationService.getNotificationsWithPagination(page, size);
    }


}
