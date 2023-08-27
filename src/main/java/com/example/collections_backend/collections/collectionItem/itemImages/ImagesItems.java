package com.example.collections_backend.collections.collectionItem.itemImages;

import com.example.collections_backend.collections.collectionItem.CollectionItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_images_collection_item")
public class ImagesItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDateTime date;

    @ManyToOne
    @JsonIgnore
    private CollectionItem collectionItem;
}
