package com.example.collections_backend.collections;

import com.example.collections_backend.dto.collectionDto.NewCollectionDto;
import com.example.collections_backend.dto.collectionDto.ReturnCollectionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CollectionController {
    private final CollectionService collectionService;

    @GetMapping("collections/{username}")
    public Iterable<Collection> getCollectionsInfo(@PathVariable(value = "username") String username) {
        return collectionService.getCollectionsInfo(username);
    }

    @GetMapping("collection/{username}/{idCollection}")
    public ReturnCollectionDto getCollectionInfo(
            @PathVariable(value = "idCollection") Long id,
            @PathVariable(value = "username") String username
    ) {
        return collectionService.getCollectionInfo(id, username);
    }

    @PostMapping(path ="auth/collection/new", consumes = "multipart/form-data")
    public ResponseEntity<String> addCollection(@ModelAttribute NewCollectionDto request) throws IOException {
        return ResponseEntity.ok(collectionService.newCollection(request));
    }


}
