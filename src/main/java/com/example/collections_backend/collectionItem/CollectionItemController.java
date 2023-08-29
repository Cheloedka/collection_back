package com.example.collections_backend.collectionItem;

import com.example.collections_backend.dto.collectionItemDto.NewItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class CollectionItemController {

    private final CollectionItemService collectionItemService;

    @PostMapping(value = "auth/item/new", consumes = "multipart/form-data")
    public ResponseEntity<String> addItem(@ModelAttribute NewItemDto request) throws IOException {
        return ResponseEntity.ok(collectionItemService.newItem(request));
    }
}
