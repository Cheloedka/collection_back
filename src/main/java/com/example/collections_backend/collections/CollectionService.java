package com.example.collections_backend.collections;

import com.example.collections_backend.collectionItem.CollectionItemRepository;
import com.example.collections_backend.collectionItem.CollectionItemService;
import com.example.collections_backend.dto.collectionDto.NewCollectionDto;
import com.example.collections_backend.dto.collectionDto.ReturnCollectionDto;
import com.example.collections_backend.exception_handling.exceptions.EntityNotFoundException;
import com.example.collections_backend.files.FileService;
import com.example.collections_backend.user.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CollectionService {
    private final CollectionRepository collectionRepository;
    private final UserManagementService userManagementService;
    private final CollectionItemService collectionItemService;
    private final CollectionItemRepository collectionItemRepository;
    private final FileService fileService;

    public Iterable<CollectionEntity> getCollectionsInfo(String username) {
        return collectionRepository.findAllByUser(userManagementService.getUserByUsername(username));
    }

    public ReturnCollectionDto getCollectionInfo(Long id, String username){
        var collection = collectionRepository
                .findByUserAndIdCollection(userManagementService.getUserByUsername(username), id)
                .orElseThrow(EntityNotFoundException::new);
        var user = collection.getUser();
        return ReturnCollectionDto.builder()
                .name(collection.getName())
                .about(collection.getAbout())
                .information(collection.getInformation())
                .image(collection.getImage())
                .backgroundImage(collection.getBackgroundImage())
                .isPrivate(collection.isPrivate())
                .userFirstName(user.getName())
                .userSurname(user.getSurname())
                .userImage(user.getImage())
                .items(collectionItemService.get5topItems(collection))
                .countItems(collectionItemRepository.countAllByCollectionEntity(collection))
                .build();
    }

    public String newCollection(NewCollectionDto request) throws IOException {

        String image = "";
        String backImage = "";

        if (request.getImage() != null) {
            image = fileService.uploadFile(request.getImage());
        }
        if (request.getBackgroundImage() != null) {
            backImage = fileService.uploadFile(request.getBackgroundImage());
        }


        var collection = CollectionEntity.builder()
                .name(request.getName())
                .about(request.getAbout())
                .information(request.getInformation())
                .user(userManagementService.getCurrentUser())
                .image(image)
                .backgroundImage(backImage)
                .isPrivate(request.isPrivate())
                .build();

        collectionRepository.save(collection);

        return "Collection create successful";
    }
}
