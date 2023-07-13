package com.example.collections_backend.friendship;

import com.example.collections_backend.user.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_friendship")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idFriendship;

    @ManyToOne
    private User follower;

    @ManyToOne
    private User user;

}
