package com.example.collections_backend.collections;

import com.example.collections_backend.dto.CollectionAddDto;
import com.example.collections_backend.dto.CollectionReturnDto;
import com.example.collections_backend.exception_handling.exceptions.EntityNotFoundException;
import com.example.collections_backend.files.FileService;
import com.example.collections_backend.user.User;
import com.example.collections_backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CollectionService {
    private final CollectionRepository collectionRepository;
    private final UserService userService;
    private final FileService fileService;

    private Collection getCollectionById(Long id) {
        return collectionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Iterable<Collection> getCollectionsInfo(String username) {
        return collectionRepository.findAllByUser(userService.getUserByUsername(username));
    }

    public CollectionReturnDto getCollectionInfo(Long id, String username){
        var collection = collectionRepository.findByUserAndIdCollection(userService.getUserByUsername(username), id).orElseThrow(EntityNotFoundException::new);

        return CollectionReturnDto.builder()
                .name(collection.getName())
                .about(collection.getAbout())
                .information(collection.getInformation())
                .image(collection.getImage())
                .backgroundImage(collection.getBackgroundImage())
                .isPrivate(collection.isPrivate())
                .build();
    }

    public String newCollection(CollectionAddDto request) throws IOException {

        String image = "";
        String backImage = "";

        if (request.getImage() != null) {
            image = fileService.uploadFile(request.getImage());
        }
        if (request.getBackgroundImage() != null) {
            backImage = fileService.uploadFile(request.getBackgroundImage());
        }


        var collection = Collection.builder()
                .name(request.getName())
                .about(request.getAbout())
                .information(request.getInformation())
                .user(userService.getCurrentUser())
                .image(image)
                .backgroundImage(backImage)
                .isPrivate(request.isPrivate())
                .build();

        collectionRepository.save(collection);

        return "Collection create successful";
    }

    public String removeCollection(Long id) {
        collectionRepository.delete(getCollectionById(id));
        return "Collection removed";
    }

    public String editCollection(Long id, CollectionAddDto request) {
        var collection = getCollectionById(id);

        if(request.getName() != null) {
            collection.setName(request.getName());
        }
        if(request.getAbout() != null) {
            collection.setAbout(request.getAbout());
        }
        if(request.getInformation() != null) {
            collection.setInformation(request.getInformation());
        }

        collectionRepository.save(collection);
        return "Collection edit successful";
    }
}
