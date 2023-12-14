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
    @JoinColumn(
            nullable = false,
            name = "follower_id_user"
    )
    private User follower; //someone who follow on

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id_user"
    )
    private User following;

}
