package com.example.collections_backend.collections;

import com.example.collections_backend.collectionItem.CollectionItem;
import com.example.collections_backend.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_collection")
public class CollectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCollection;
    private String name;
    private String about;
    private String information;
    private String image;
    private String backgroundImage;
    private boolean isPrivate;

    @OneToMany(mappedBy = "collectionEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CollectionItem> items;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(
            nullable = false,
            name = "id_user"
    )
    private User user;

}
