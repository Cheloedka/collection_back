package com.example.collections_backend.notifications;

import com.example.collections_backend.collectionItem.CollectionItem;
import com.example.collections_backend.commentary.Commentary;
import com.example.collections_backend.user.User;
import com.example.collections_backend.user.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserManagementService userManagementService;

    private final NotificationRepository notificationRepository;

    private final List<Integer> upvoteCounts = List.of(2, 5, 10, 25, 50);


    private void saveAndSendToUser(Notification.NotificationBuilder builder) {
        var notification = builder.build();
        notificationRepository.save(notification);

        simpMessagingTemplate.convertAndSendToUser(  //sends to user with WebSockets
                notification.getNotifiedUser().getNickname(),
                "/receive-notification",
                notification.createDto()
        );
    }


    private Notification.NotificationBuilder builder(User notifiedUser, NotificationType type) {
        return Notification.builder()
                .notifiedUser(notifiedUser)
                .type(type)
                .isRead(false);
    }


    public void createAddFriendNotification(User toUser, User fromUser) {
        saveAndSendToUser(
                builder(toUser, NotificationType.ADD_FRIEND)
                        .secondUser(fromUser)
        );
    }

    public void createNewCommentNotification(Commentary comment, Commentary repliedComment) {
        var commentator = userManagementService.getCurrentUser();

        if (repliedComment != null) {
            //if user replies on his own commentary, no notification will be sent
            if (!commentator.isTheSameUserEntity(repliedComment.getAuthor())) {
                saveAndSendToUser(
                        builder(repliedComment.getAuthor(), NotificationType.COMMENT_REPLY)
                                .commentary(comment)
                                .secondUser(commentator)
                );
            }
        }
        else {
            //sends notification to post creator
            var postCreator = comment.getAnswerToItem().getCollectionEntity().getUser();
            if (!commentator.isTheSameUserEntity(postCreator)
            ) {
                saveAndSendToUser(
                        builder(comment.getAnswerToItem().getCollectionEntity().getUser(), NotificationType.POST_REPLY)
                                .commentary(comment)
                                .secondUser(commentator)
                );
            }
        }
    }


    public <T> void createUpvotesNotification(T obj, int upvotesCount) {
        if (upvoteCounts.contains(upvotesCount)) {
            if (obj instanceof CollectionItem)
                createPostUpvotesNotification((CollectionItem) obj, upvotesCount);
            else if (obj instanceof Commentary)
                createCommentUpvotesNotification((Commentary) obj, upvotesCount);
            else
                throw new RuntimeException("Method doesn't allow this type of class");
        }
    }

    private void createPostUpvotesNotification(CollectionItem post, int upvotesCount) {
        saveAndSendToUser(
                builder(post.getCollectionEntity().getUser(), NotificationType.POST_UPVOTE)
                        .message(String.valueOf(upvotesCount))
                        .post(post)
        );
    }

    private void createCommentUpvotesNotification(Commentary commentary, int upvotesCount) {
        saveAndSendToUser(
                builder(commentary.getAuthor(), NotificationType.COMMENT_UPVOTE)
                        .message(String.valueOf(upvotesCount))
                        .commentary(commentary)
        );
    }


    @Transactional
    public void readNotificationsOfCurrentUser() {
        notificationRepository.readNotificationsOfUser(userManagementService.getCurrentUser());
    }


    public List<NotificationDto> getNotificationsWithPagination(int page, int size) {
        var pageable = PageRequest.of(page, size);

        return notificationRepository.getAllByNotifiedUser(userManagementService.getCurrentUser(), pageable)
                .stream()
                .map(Notification::createDto)
                .toList();
    }


    public int countUnreadNotifications(User user) {
        return notificationRepository.countUnreadNotificationsByUser(user);
    }


}
