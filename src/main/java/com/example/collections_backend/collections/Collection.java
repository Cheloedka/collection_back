package com.example.collections_backend.collections;

import com.example.collections_backend.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_collection")
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCollection;
    private String name;
    private String about;
    private String information;
    private String image;
    private String backgroundImage;
    private boolean isPrivate;


    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "id_user"
    )
    @JsonIgnore
    private User user;

}
