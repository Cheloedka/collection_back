package com.example.collections_backend.collectionItem;

import com.example.collections_backend.collectionItem.itemImages.ImagesItem;
import com.example.collections_backend.collectionItem.itemLikes.LikeRepository;
import com.example.collections_backend.collectionItem.itemLikes.LikeService;
import com.example.collections_backend.collections.CollectionEntity;
import com.example.collections_backend.collections.CollectionManagementService;
import com.example.collections_backend.collectionItem.itemImages.ImagesItemRepository;
import com.example.collections_backend.collectionItem.itemImages.ImagesItemService;
import com.example.collections_backend.commentary.CommentaryRepository;
import com.example.collections_backend.dto.collectionItemDto.*;
import com.example.collections_backend.exception_handling.exceptions.EntityNotFoundException;
import com.example.collections_backend.files.FileService;
import com.example.collections_backend.friendship.FriendshipRepository;
import com.example.collections_backend.user.UserManagementService;
import com.example.collections_backend.utils.ConsumerFunctions;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CollectionItemService {

    private final CollectionManagementService collectionManagementService;
    private final CollectionItemRepository collectionItemRepository;
    private final FileService fileService;
    private final ImagesItemService imagesItemService;
    private final ImagesItemRepository imagesItemRepository;
    private final LikeService likeService;
    private final LikeRepository likeRepository;
    private final CommentaryRepository commentaryRepository;
    private final UserManagementService userManagementService;
    private final FriendshipRepository friendshipRepository;


    private List<GetItemInfoDto> listItemsToListDto(List<CollectionItem> items) {
        return items.stream().map(i -> GetItemInfoDto.builder()
                        .name(i.getName())
                        .about(i.getAbout())
                        .information(i.getInformation())
                        .images(imagesItemRepository.findAllByCollectionItem(i))
                        .itemId(i.getId())
                        .liked(likeService.isExistLike(i.getId()))
                        .likesCount(likeRepository.countAllByCollectionItem(i))
                        .commentsCount(commentaryRepository.countAllByAnswerToItem(i))
                        .countId(i.getCountId())
                        .collectionId(i.getCollectionEntity().getIdCollection())
                        .infoName(i.getCollectionEntity().getName())
                        .infoImage(i.getCollectionEntity().getImage())
                        .creationTime(i.getCreationTime())
                        .author(i.getCollectionEntity().getUser().getNickname())
                        .build()
                )
                .toList();
    }

    private CollectionItem getItemByIdCollectionAndIdItem(Integer idItem, Long idCollection) {
        return collectionItemRepository
                .findByCollectionEntityAndCountId(collectionManagementService.findById(idCollection), idItem)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void deleteAllImagesFromStorageByItem(CollectionItem item) {
        List<ImagesItem> images = imagesItemRepository.findAllByCollectionItem(item);
        if (images.size() != 0) {
            images.forEach(image -> fileService.deleteImageFromStorage(image.getName()));
        }
    }

    public void deleteItem(Long id) {
        var item = collectionItemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        deleteAllImagesFromStorageByItem(item);

        collectionItemRepository.delete(item);
    }

    public void editItem(EditItemDto request) {
        var item = getItemByIdCollectionAndIdItem(request.getCountId(), request.getIdCollection());

        ConsumerFunctions.setIfNotNull(request.getName(), item::setName);
        ConsumerFunctions.setIfNotNull(request.getAbout(), item::setAbout);
        ConsumerFunctions.setIfNotNull(request.getInformation(), item::setInformation);

        if (request.getOldImages() != null) {
            List<String> itemImages = imagesItemRepository.findAllByCollectionItem(item).stream()
                    .map(ImagesItem::getName)
                    .collect(Collectors.toCollection(ArrayList::new));


            List<String> imagesToDelete = fileService.changeItemImages(request.getOldImages(), itemImages);

            if (imagesToDelete.size() > 0) {
                imagesItemService.deleteImages(imagesToDelete);
            }
        }


        if (request.getNewImages() != null) {
            imagesItemService.saveImages(
                    fileService.uploadItemImages(request.getNewImages()),
                    item
            );
        }


        collectionItemRepository.save(item);
    }

    public String newItem(NewItemDto request) {

        var collectionEntity = collectionManagementService.findById(request.getIdCollection());
        var lastItem = collectionItemRepository.findTopByCollectionEntityOrderByCountIdDesc(collectionEntity);

        var item = CollectionItem.builder()
                .name(request.getName())
                .countId(lastItem.map(collectionItem -> collectionItem.getCountId() + 1).orElse(1))
                .about(request.getAbout())
                .information(request.getInformation())
                .collectionEntity(collectionEntity)
                .build();

        collectionItemRepository.save(item);

        if (request.getImages() != null) {
            imagesItemService.saveImages(fileService.uploadItemImages(request.getImages()), item);
        }

        return "Success";
    }

    public List<GetShortItemInfoDto> getAllShortCollectionItems(CollectionEntity collectionEntity) {

        var list = collectionItemRepository.findAllByCollectionEntity(collectionEntity);

        return list.stream().map(i -> GetShortItemInfoDto.builder()
                .itemAbout(i.getAbout())
                .itemName(i.getName())
                .itemImage(imagesItemRepository.findTop1ByCollectionItem(i).get().getName())
                .countId(i.getCountId())
                .build()
        ).toList();
    }

    public List<GetItemInfoDto> getAllCollectionItems(Long idCollection, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("creationTime").descending());

        return listItemsToListDto(
                collectionItemRepository.findAllByCollectionEntity_IdCollection(idCollection, pageable)
        );
    }

    public List<GetItemInfoDto> getItemsByFriendships(String username, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        var user = userManagementService.getUserByUsername(username);
        var listUsers = friendshipRepository.findAllByFollower(user)
                .stream()
                .map(f -> f.getFollowing().getNickname())
                .toList();

        return listItemsToListDto(
                collectionItemRepository.findPosts(listUsers, pageable)
        );
    }

    public List<GetItemInfoDto> getMainItems(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        var startDate = LocalDateTime.now().minusDays(30);
        return listItemsToListDto(
                collectionItemRepository.getPopularPostsBeforeDate(startDate, pageable)
        );
    }

    public List<GetItemInfoDto> getAllItemsByUsername(String username,  int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("creationTime").descending());
        return listItemsToListDto(
                collectionItemRepository.findAllByCollectionEntity_User_Username(username, pageable)
        );
    }

    public GetItemInfoDto getItemInfo(Integer idItem, Long idCollection, String username) {

        var collection = collectionManagementService.findById(idCollection);

        if ( !username.equals(collection.getUser().getNickname()) ) {
            throw new EntityNotFoundException();
        }

        var item = getItemByIdCollectionAndIdItem(idItem, idCollection);

        return GetItemInfoDto.builder()
                .name(item.getName())
                .about(item.getAbout())
                .information(item.getInformation())
                .images(imagesItemRepository.findAllByCollectionItem(item))
                .liked(likeService.isExistLike(item.getId()))
                .itemId(item.getId())
                .likesCount(likeRepository.countAllByCollectionItem(item))
                .isPrivate(item.getCollectionEntity().isPrivate())
                .build();
    }

    public GetSetItemForEditorDto getItemForEditor(Integer idItem, Long idCollection) {
        var item = getItemByIdCollectionAndIdItem(idItem, idCollection);

        return GetSetItemForEditorDto.builder()
                .name(item.getName())
                .about(item.getAbout())
                .information(item.getInformation())
                .images(imagesItemRepository.findAllByCollectionItem(item))
                .build();
    }
}
