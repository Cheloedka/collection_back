package com.example.collections_backend.collectionItem;

import com.example.collections_backend.collectionItem.itemImages.ImagesItem;
import com.example.collections_backend.collections.CollectionEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_collection_item")
public class CollectionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String about;
    private String information;

    @OneToMany(mappedBy = "collectionItem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ImagesItem> images;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_collection")
    private CollectionEntity collectionEntity;
}
