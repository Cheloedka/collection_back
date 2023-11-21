package com.example.collections_backend.commentary;

import com.example.collections_backend.collectionItem.CollectionItem;
import com.example.collections_backend.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


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

    @Column(columnDefinition = "boolean default false")
    boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "author")
    @CreatedBy
    private User author;

    @ManyToOne
    @JoinColumn(name = "answer_to_post")
    private CollectionItem answerToItem;

    @ManyToOne
    @JoinColumn(name = "answer_to_commentary")
    private Commentary answerToId;

    @Builder
    public Commentary(String content, Commentary answerToId, CollectionItem answerToItem, boolean isDeleted) {
        this.content = content;
        this.answerToId = answerToId;
        this.answerToItem = answerToItem;
        this.isDeleted = isDeleted;
    }
}
