package com.example.collections_backend.collectionItem;

import com.example.collections_backend.dto.collectionItemDto.EditItemDto;
import com.example.collections_backend.dto.collectionItemDto.GetSetItemForEditorDto;
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
    public ResponseEntity<String> addItem(@ModelAttribute NewItemDto request) {
        return ResponseEntity.ok(collectionItemService.newItem(request));
    }

    @GetMapping("auth/itemForEditor/{idCollection}/{idItem}")
    public GetSetItemForEditorDto getItemForEditor(@PathVariable(value = "idCollection") Long idCollection,
                                                   @PathVariable(value = "idItem") Integer idItem
                                                ) {
        return collectionItemService.getItemForEditor(idItem, idCollection);
    }

    @GetMapping("item/{idCollection}/{idItem}")
    public GetItemInfoDto getItem(@PathVariable(value = "idCollection") Long idCollection,
                                  @PathVariable(value = "idItem") Integer idItem
                                  ) {
        return collectionItemService.getItemInfo(idItem, idCollection);
    }

    @PutMapping(value = "auth/item/edit", consumes = "multipart/form-data")
    public ResponseEntity<String> editItem(@ModelAttribute EditItemDto editItemDto) {

        collectionItemService.editItem(editItemDto);
        return ResponseEntity.ok("Success");
    }
}
