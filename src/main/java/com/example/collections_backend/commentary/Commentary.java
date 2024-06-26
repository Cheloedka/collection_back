package com.example.collections_backend.commentary;

import com.example.collections_backend.collectionItem.CollectionItem;
import com.example.collections_backend.commentary.like.CommentaryLike;
import com.example.collections_backend.notifications.Notification;
import com.example.collections_backend.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Table(name = "_commentary")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Commentary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @CreatedDate
    private LocalDateTime creationDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @ManyToOne
    @JoinColumn(name = "author", nullable = true)
    @CreatedBy
    private User author;


    @ManyToOne
    @JoinColumn(name = "answer_to_post")
    private CollectionItem answerToItem;

    @ManyToOne
    @JoinColumn(name = "answer_to_commentary")
    private Commentary answerToId;

    @OneToMany(mappedBy = "answerToId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Commentary> answered;

    @OneToMany(mappedBy = "commentary", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentaryLike> likes;

    @OneToMany(mappedBy = "commentary", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Notification> notifications;

    @Builder
    public Commentary(String content, Commentary answerToId, CollectionItem answerToItem) {
        this.content = content;
        this.answerToId = answerToId;
        this.answerToItem = answerToItem;
    }
}
