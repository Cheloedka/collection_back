package com.example.collections_backend.collectionItem.itemImages;

import com.example.collections_backend.collectionItem.CollectionItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ImagesItemService {

    private final ImagesItemRepository imagesItemRepository;

    public void saveImages(ArrayList<String> images, CollectionItem item) {

        for(String image: images) {
            var element = ImagesItem.builder()
                    .name(image)
                    .date(LocalDateTime.now())
                    .collectionItem(item)
                    .build();
            imagesItemRepository.save(element);
        }
    }
}
