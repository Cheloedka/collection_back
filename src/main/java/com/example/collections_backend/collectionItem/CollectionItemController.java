package com.example.collections_backend.collectionItem;

import com.example.collections_backend.dto.collectionItemDto.EditItemDto;
import com.example.collections_backend.dto.collectionItemDto.GetSetItemForEditorDto;
import com.example.collections_backend.dto.collectionItemDto.GetItemInfoDto;
import com.example.collections_backend.dto.collectionItemDto.NewItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class CollectionItemController {

    private final CollectionItemService collectionItemService;

    @PostMapping(value = "auth/item/new", consumes = "multipart/form-data")
    public ResponseEntity<String> addItem(@ModelAttribute NewItemDto request) {
        return ResponseEntity.ok(collectionItemService.newItem(request));
    }

    @GetMapping(value = "/item/{username}/all")
    public List<GetItemInfoDto> getAllItems(@PathVariable String username) {
        return collectionItemService.getAllItemsByUsername(username);
    }

    @GetMapping("auth/itemForEditor/{idCollection}/{idItem}")
    public GetSetItemForEditorDto getItemForEditor(@PathVariable Long idCollection,
                                                   @PathVariable Integer idItem
                                                ) {
        return collectionItemService.getItemForEditor(idItem, idCollection);
    }

    @GetMapping("item/{username}/{idCollection}/{idItem}")
    public GetItemInfoDto getItem(@PathVariable Long idCollection,
                                  @PathVariable Integer idItem,
                                  @PathVariable String username
                                  ) {
        return collectionItemService.getItemInfo(idItem, idCollection, username);
    }

    @PutMapping(value = "auth/item/edit", consumes = "multipart/form-data")
    public ResponseEntity<String> editItem(@ModelAttribute EditItemDto editItemDto) {

        collectionItemService.editItem(editItemDto);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("auth/item/delete/{idItem}")
    public ResponseEntity<String> deleteItem (@PathVariable Long idItem) {
        collectionItemService.deleteItem(idItem);
        return ResponseEntity.ok("Success");
    }
}
