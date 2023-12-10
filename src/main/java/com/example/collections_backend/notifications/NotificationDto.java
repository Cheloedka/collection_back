package com.example.collections_backend.notifications;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDto {

    private boolean isRead;
    private NotificationType type;
    private LocalDateTime date;
    private String message;

    private String nickname;
    private Integer itemId;
    private Long collectionId;
    private String ownerItem;

}
