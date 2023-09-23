package com.example.collections_backend.collectionItem.itemImages;

import com.example.collections_backend.collectionItem.CollectionItem;
import com.example.collections_backend.exception_handling.exceptions.EntityNotFoundException;
import com.example.collections_backend.exception_handling.exceptions.SomethingNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImagesItemService {

    private final ImagesItemRepository imagesItemRepository;

    public void saveImages(List<String> images, CollectionItem item) {

        images.forEach(image -> {
            var element = ImagesItem.builder()
                    .name(image)
                    .date(LocalDateTime.now())
                    .collectionItem(item)
                    .build();
            imagesItemRepository.save(element);
        });
    }

    public void deleteImages(List<String> images) {
        images.forEach(image -> {
            var img = imagesItemRepository.findByName(image).orElseThrow(EntityNotFoundException::new);
            imagesItemRepository.delete(img);
        });
    }
}
