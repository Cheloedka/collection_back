package com.example.collections_backend.collectionItem.itemLikes;

import com.example.collections_backend.collectionItem.CollectionItem;
import com.example.collections_backend.collectionItem.CollectionItemRepository;
import com.example.collections_backend.exception_handling.exceptions.SomethingNotFoundException;
import com.example.collections_backend.user.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final CollectionItemRepository collectionItemRepository;
    private final UserManagementService userManagementService;
    private final LikeRepository likeRepository;

    private CollectionItem getItem(Long id) {
        return collectionItemRepository.findById(id).orElseThrow(() -> new SomethingNotFoundException("Item not found"));
    }

    public String newLike(Long id) {
        var user = userManagementService.getCurrentUser();
        var like = LikeItem.builder()
                .collectionItem(getItem(id))
                .user(user)
                .build();

        likeRepository.save(like);
        return "Success";
    }

    public String deleteLike(Long id) {

        var like = likeRepository.findByCollectionItemAndUser(
                getItem(id), userManagementService.getCurrentUser()
        )
                .orElseThrow(() -> new SomethingNotFoundException("Like not found"));

        likeRepository.delete(like);
        return "Deleted";
    }

    public boolean isExistLike(Long id) {
        return likeRepository.existsByCollectionItemAndUser(getItem(id), userManagementService.getCurrentUser());

    }

}
