package com.example.collections_backend.collectionItem.itemLikes;


import com.example.collections_backend.collectionItem.CollectionItem;
import com.example.collections_backend.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_like_item")
public class LikeItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_item")
    private CollectionItem collectionItem;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_user")
    private User user;
}
