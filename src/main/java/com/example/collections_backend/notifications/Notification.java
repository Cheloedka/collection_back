package com.example.collections_backend.notifications;

import com.example.collections_backend.collectionItem.CollectionItem;
import com.example.collections_backend.commentary.Commentary;
import com.example.collections_backend.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_account")
    private User notifiedUser;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    boolean isRead;

    @CreatedDate
    private LocalDateTime date;

    private String message;

    //might be null
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "second_user_id")
    private User secondUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "commentary_id")
    private Commentary commentary;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private CollectionItem post;


    public NotificationDto createDto() {
        var builder = NotificationDto.builder()
                .isRead(isRead)
                .type(type)
                .date(date)
                .message(message)
                .nickname(secondUser == null ? null : secondUser.getNickname())
                .itemId(post == null ? null : post.getCountId())
                .collectionId(post == null ? null : post.getCollectionEntity().getIdCollection())
                .ownerItem(post == null ? null : post.getCollectionEntity().getUser().getNickname());

        if (commentary != null) {
            var s = commentary.getContent();
            builder
                    .itemId(commentary.getAnswerToItem().getCountId())
                    .collectionId(commentary.getAnswerToItem().getCollectionEntity().getIdCollection())
                    .ownerItem(commentary.getAnswerToItem().getCollectionEntity().getUser().getNickname())
                    .message(message == null ? (s.length() > 100 ? s.substring(0, 100) : s) : message);
        }
        return builder.build();
    }
}
