package com.example.collections_backend.collections;

import com.example.collections_backend.collectionItem.CollectionItemRepository;
import com.example.collections_backend.collectionItem.CollectionItemService;
import com.example.collections_backend.dto.collectionDto.NewAndChangeCollectionDto;
import com.example.collections_backend.dto.collectionDto.ReturnCollectionDto;
import com.example.collections_backend.dto.collectionDto.RightInfoInCollectionAndItemPageDto;
import com.example.collections_backend.exception_handling.exceptions.EntityNotFoundException;
import com.example.collections_backend.files.FileService;
import com.example.collections_backend.user.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CollectionService {
    private final CollectionRepository collectionRepository;
    private final UserManagementService userManagementService;
    private final CollectionItemService collectionItemService;
    private final CollectionItemRepository collectionItemRepository;
    private final CollectionManagementService collectionManagementService;
    private final FileService fileService;

    public Iterable<CollectionEntity> getCollectionsInfo(String username) {
        return collectionRepository.findAllByUser(userManagementService.getUserByUsername(username));
    }

    public RightInfoInCollectionAndItemPageDto getRightInfo(Long id) {
        var collection = collectionManagementService.findById(id);

        return RightInfoInCollectionAndItemPageDto.builder()
                .nameCollection(collection.getName())
                .aboutCollection(collection.getAbout())
                .imageCollection(collection.getImage())
                .firstName(collection.getUser().getName())
                .surname(collection.getUser().getSurname())
                .userImage(collection.getUser().getImage())
                .build();
    }

    public ReturnCollectionDto getCollectionInfo(Long id, String username){
        var collection = collectionRepository
                .findByUserAndIdCollection(userManagementService.getUserByUsername(username), id)
                .orElseThrow(EntityNotFoundException::new);
        return ReturnCollectionDto.builder()
                .name(collection.getName())
                .about(collection.getAbout())
                .information(collection.getInformation())
                .image(collection.getImage())
                .backgroundImage(collection.getBackgroundImage())
                .isPrivate(collection.isPrivate())
                .items(collectionItemService.get5topItems(collection))
                .countItems(collectionItemRepository.countAllByCollectionEntity(collection))
                .build();
    }

    public String newCollection(NewAndChangeCollectionDto request) throws IOException {

        var collection = CollectionEntity.builder()
                .name(request.getName())
                .about(request.getAbout())
                .information(request.getInformation())
                .user(userManagementService.getCurrentUser())
                .image(
                        isImageNotNullUpload(request.getImage())
                )
                .backgroundImage(
                        isImageNotNullUpload(request.getBackgroundImage())
                )
                .isPrivate(request.isPrivate())
                .build();

        collectionRepository.save(collection);

        return "Collection create successful";
    }

    public String changeCollection(NewAndChangeCollectionDto request, Long id) throws IOException {

        var collection = collectionRepository.findById( id).orElseThrow(EntityNotFoundException::new);
        if (request.getName() != null) {
            collection.setName(request.getName());
        }
        if (request.getAbout() != null) {
            collection.setAbout(request.getAbout());
        }
        if (request.getInformation() != null) {
            collection.setInformation(request.getInformation());
        }
        if (request.isPrivate() != collection.isPrivate()) {
            collection.setPrivate(request.isPrivate());
        }

        if (request.getBackgroundImage() != null) {

            if (!collection.getBackgroundImage().isEmpty()) {
                fileService.deleteImageFromStorage(
                        collection.getBackgroundImage()
                );
            }
            collection.setBackgroundImage(
                    fileService.uploadFile(request.getBackgroundImage())
            );
        }

        if (request.getImage() != null) {

            if (!collection.getImage().isEmpty()) {
                fileService.deleteImageFromStorage(
                        collection.getImage()
                );
            }
            collection.setImage(
                    fileService.uploadFile(request.getImage())
            );
        }

        collectionRepository.save(collection);

        return "Collection changed successful";
    }

    public String isImageNotNullUpload(MultipartFile image) throws IOException {
        if (image != null) {
            return fileService.uploadFile(image);
        }
        return "";
    }
}
