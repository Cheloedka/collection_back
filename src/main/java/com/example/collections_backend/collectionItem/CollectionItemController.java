package com.example.collections_backend.collectionItem;

import com.example.collections_backend.dto.collectionItemDto.GetItemInfoDto;
import com.example.collections_backend.dto.collectionItemDto.NewItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("item/{idCollection}/{idItem}")
    public GetItemInfoDto getItem(@PathVariable(value = "idCollection") Long idCollection,
                                  @PathVariable(value = "idItem") Integer idItem
                                  ){
        return collectionItemService.getItemInfo(idItem, idCollection);
    }
}
