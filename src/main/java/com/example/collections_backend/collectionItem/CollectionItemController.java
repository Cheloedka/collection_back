package com.example.collections_backend.collectionItem;

import com.example.collections_backend.dto.collectionItemDto.EditItemDto;
import com.example.collections_backend.dto.collectionItemDto.GetSetItemForEditorDto;
import com.example.collections_backend.dto.collectionItemDto.GetItemInfoDto;
import com.example.collections_backend.dto.collectionItemDto.NewItemDto;
import com.example.collections_backend.exception_handling.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "item/{type}/{info}")
    public List<GetItemInfoDto> getAllItemsByType(@PathVariable String info,
                                                  @PathVariable String type,
                                                  @RequestParam(defaultValue = "0", required = false) int page,
                                                  @RequestParam(defaultValue = "4", required = false) int pageSize
    ) {
        switch (type) {
            case "user":
                return collectionItemService.getAllItemsByUsername(info, page, pageSize);
            case "collection":
                return collectionItemService.getAllCollectionItems(Long.valueOf(info), page, pageSize);
            case "friendship":
                return collectionItemService.getItemsByFriendships(info, page, pageSize);
            case "main":
                return collectionItemService.getMainItems(page, pageSize);
        }

        throw new BadRequestException("There is not such search type as \"" + type + "\" ");
    }

    @GetMapping("auth/itemForEditor/{idCollection}/{idItem}")
    public GetSetItemForEditorDto getItemForEditor(@PathVariable Long idCollection, @PathVariable Integer idItem) {
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
