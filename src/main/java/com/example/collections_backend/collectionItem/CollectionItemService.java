package com.example.collections_backend.collectionItem;

import com.example.collections_backend.collectionItem.itemLikes.LikeRepository;
import com.example.collections_backend.collectionItem.itemLikes.LikeService;
import com.example.collections_backend.collections.CollectionEntity;
import com.example.collections_backend.collections.CollectionManagementService;
import com.example.collections_backend.collectionItem.itemImages.ImagesItemRepository;
import com.example.collections_backend.collectionItem.itemImages.ImagesItemService;
import com.example.collections_backend.dto.collectionItemDto.GetSetItemForEditorDto;
import com.example.collections_backend.dto.collectionItemDto.GetItemInfoDto;
import com.example.collections_backend.dto.collectionItemDto.GetShortItemInfoDto;
import com.example.collections_backend.dto.collectionItemDto.NewItemDto;
import com.example.collections_backend.exception_handling.exceptions.EntityNotFoundException;
import com.example.collections_backend.files.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                .findByCollectionEntityAndAndCountId(collectionManagementService.findById(idCollection), idItem)
                .orElseThrow(EntityNotFoundException::new);
    }

    public String newItem(NewItemDto request) throws IOException {

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
            imagesItemService.saveImages(
                    fileService.uploadItemImages(request.getImages()),
                    item
            );
        }

        return "Success";
    }

    public List<GetShortItemInfoDto> get5topItems(CollectionEntity collectionEntity) {
        List<CollectionItem> items = new ArrayList<>(
                collectionItemRepository.findTop5ByCollectionEntity(collectionEntity)
        );
        List<GetShortItemInfoDto> dtos = new ArrayList<>();
        for (CollectionItem item : items) {

            String image = "";
            if(imagesItemRepository.findTop1ByCollectionItem(item).isPresent()) {
                image = imagesItemRepository.findTop1ByCollectionItem(item)
                        .get()
                        .getName();
            }

            var newItem = GetShortItemInfoDto.builder()
                    .itemName(item.getName())
                    .itemAbout(item.getAbout())
                    .itemImage(image)
                    .countId(item.getCountId())
                    .itemId(item.getId())
                    .liked(likeService.isExistLike(item.getId()))
                    .build();
            dtos.add(newItem);
        }

        return dtos;
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
