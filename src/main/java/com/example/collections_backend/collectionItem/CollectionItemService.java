package com.example.collections_backend.collectionItem;

import com.example.collections_backend.collections.CollectionEntity;
import com.example.collections_backend.collections.CollectionManagementService;
import com.example.collections_backend.collectionItem.itemImages.ImagesItemRepository;
import com.example.collections_backend.collectionItem.itemImages.ImagesItemService;
import com.example.collections_backend.dto.collectionItemDto.GetItemDto;
import com.example.collections_backend.dto.collectionItemDto.NewItemDto;
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

    public String newItem(NewItemDto request) throws IOException {

        var item = CollectionItem.builder()
                .name(request.getName())
                .about(request.getAbout())
                .information(request.getInformation())
                .collectionEntity(collectionManagementService.findById(request.getIdCollection()))
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

    public List<GetItemDto> get5topItems(CollectionEntity collectionEntity) {
        List<CollectionItem> items = new ArrayList<>(collectionItemRepository.findTop5ByCollectionEntity(collectionEntity));
        List<GetItemDto> dtos = new ArrayList<>();
        for (CollectionItem item : items) {
            String image = "";
            if(imagesItemRepository.findTop1ByCollectionItem(item).isPresent()) {
                image = imagesItemRepository.findTop1ByCollectionItem(item).get().getName();
            }

            var newItem = GetItemDto.builder()
                    .itemName(item.getName())
                    .itemAbout(item.getAbout())
                    .itemImage(image)
                    .build();
            dtos.add(newItem);
        }

        return dtos;
    }
}
