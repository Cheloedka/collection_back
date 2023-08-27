package com.example.collections_backend.collections.collectionItem;

import com.example.collections_backend.dto.collectionItemDto.NewItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CollectionItemService {

    public String newItem(NewItemDto request) {

        if (request.getImages().size() > 10) {

        }


        return "Success";
    }
}
