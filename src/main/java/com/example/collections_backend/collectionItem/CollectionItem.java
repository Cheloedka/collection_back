package com.example.collections_backend.collectionItem;

import com.example.collections_backend.collectionItem.itemImages.ImagesItem;
import com.example.collections_backend.collectionItem.itemLikes.LikeItem;
import com.example.collections_backend.collections.CollectionEntity;
import com.example.collections_backend.commentary.Commentary;
import com.example.collections_backend.notifications.Notification;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_collection_item")
@EntityListeners(AuditingEntityListener.class)
public class CollectionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer countId;

    private String name;
    private String about;
    private String information;

    @CreatedDate
    private LocalDateTime creationTime;

    @OneToMany(mappedBy = "collectionItem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ImagesItem> images;

    @OneToMany(mappedBy = "collectionItem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LikeItem> likes;

    @OneToMany(mappedBy = "answerToItem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Commentary> commentaries;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Notification> notifications;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_collection")
    private CollectionEntity collectionEntity;
}
