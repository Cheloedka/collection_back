package com.example.collections_backend.collections;

import com.example.collections_backend.collectionItem.CollectionItem;
import com.example.collections_backend.collectionItem.CollectionItemRepository;
import com.example.collections_backend.collectionItem.CollectionItemService;
import com.example.collections_backend.dto.collectionDto.NewAndChangeCollectionDto;
import com.example.collections_backend.dto.collectionDto.ReturnCollectionDto;
import com.example.collections_backend.dto.collectionDto.RightInfoInCollectionAndItemPageDto;
import com.example.collections_backend.exception_handling.exceptions.EntityNotFoundException;
import com.example.collections_backend.files.FileService;
import com.example.collections_backend.user.UserManagementService;
import com.example.collections_backend.utils.ConsumerFunctions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CollectionService {
    private final CollectionRepository collectionRepository;
    private final UserManagementService userManagementService;
    private final CollectionItemService collectionItemService;
    private final CollectionItemRepository collectionItemRepository;
    private final CollectionManagementService collectionManagementService;
    private final FileService fileService;

    public List<CollectionEntity> getCollectionsInfo(String username) {
        return collectionRepository.findAllByUser(userManagementService.getUserByUsername(username));
    }

    public void deleteCollection(Long id) throws FileNotFoundException {
        var collection = collectionManagementService.findById(id);
        if (!collection.getImage().equals("")) {
            fileService.deleteImageFromStorage(collection.getImage());
        }
        if(!collection.getBackgroundImage().equals("")) {
            fileService.deleteImageFromStorage(collection.getBackgroundImage());
        }

        List<CollectionItem> items = collectionItemRepository.findAllByCollectionEntity(collection);

        if(items.size() != 0) {
            items.forEach(collectionItemService::deleteAllImagesFromStorageByItem);
        }

        collectionRepository.deleteById(id);
    }

    public Boolean isUserOwner(Long id) {
        return collectionManagementService.findById(id).getUser().getName()
                .equals(userManagementService.getCurrentUser().getName());
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
                .isPrivate(request.getIsPrivate())
                .build();

        collectionRepository.save(collection);

        return "Collection create successful";
    }

    public String changeCollection(NewAndChangeCollectionDto request, Long id) throws IOException {

        var collection = collectionRepository.findById( id).orElseThrow(EntityNotFoundException::new);


        ConsumerFunctions.setIfNotNull(request.getName(), collection::setName);
        ConsumerFunctions.setIfNotNull(request.getAbout(), collection::setAbout);
        ConsumerFunctions.setIfNotNull(request.getInformation(), collection::setInformation);

        if (request.getIsPrivate() != collection.isPrivate()) {
            collection.setPrivate(request.getIsPrivate());
        }

        if (request.getBackgroundImage() != null) {

            if (!collection.getBackgroundImage().equals("")) {
                fileService.deleteImageFromStorage(
                        collection.getBackgroundImage()
                );
            }
            collection.setBackgroundImage(
                    fileService.uploadFile(request.getBackgroundImage())
            );
        }

        if (request.getImage() != null) {

            if (!collection.getImage().equals("")) {
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
