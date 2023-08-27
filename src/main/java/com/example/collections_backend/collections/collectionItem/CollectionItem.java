package com.example.collections_backend.collections.collectionItem;

import com.example.collections_backend.collections.Collection;
import com.example.collections_backend.collections.collectionItem.itemImages.ImagesItems;
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
    private List<ImagesItems> images;

    @ManyToOne
    @JsonIgnore
    private Collection collection;
}
