package com.example.collections_backend.collectionItem;

import com.example.collections_backend.collectionItem.itemImages.ImagesItem;
import com.example.collections_backend.collectionItem.itemLikes.LikeRepository;
import com.example.collections_backend.collectionItem.itemLikes.LikeService;
import com.example.collections_backend.collections.CollectionEntity;
import com.example.collections_backend.collections.CollectionManagementService;
import com.example.collections_backend.collectionItem.itemImages.ImagesItemRepository;
import com.example.collections_backend.collectionItem.itemImages.ImagesItemService;
import com.example.collections_backend.dto.collectionItemDto.*;
import com.example.collections_backend.exception_handling.exceptions.EntityNotFoundException;
import com.example.collections_backend.files.FileService;
import com.example.collections_backend.utils.ConsumerFunctions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
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



    private CollectionItem getItemByIdCollectionAndIdItem(Integer idItem, Long idCollection) {
        return collectionItemRepository
                .findByCollectionEntityAndCountId(collectionManagementService.findById(idCollection), idItem)
                .orElseThrow(EntityNotFoundException::new);
    }

    public String newItem(NewItemDto request) {

        var collectionEntity = collectionManagementService.findById(request.getIdCollection());
        var lastItem = collectionItemRepository.findTopByCollectionEntityOrderByCountIdDesc(collectionEntity);

        var item = CollectionItem.builder()
                .name(request.getName())
                .countId( lastItem.isPresent() ? lastItem.get().getCountId() + 1 : 1)
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


    public List<GetShortItemInfoDto> get5topItems(CollectionEntity collectionEntity) {
        List<CollectionItem> items = collectionItemRepository.findTop5ByCollectionEntity(collectionEntity);

        return items.stream().map(item -> {

            String image = "";
            var optional = imagesItemRepository.findTop1ByCollectionItem(item);
            if (optional.isPresent()) {
                image = optional.get().getName();
            }

            return GetShortItemInfoDto.builder()
                    .itemName(item.getName())
                    .itemAbout(item.getAbout())
                    .itemImage(image)
                    .countId(item.getCountId())
                    .itemId(item.getId())
                    .liked(likeService.isExistLike(item.getId()))
                    .build();
        }).toList();
    }

    public GetItemInfoDto getItemInfo(Integer idItem, Long idCollection) {
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
