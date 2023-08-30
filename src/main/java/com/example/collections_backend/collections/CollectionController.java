package com.example.collections_backend.collections;

import com.example.collections_backend.dto.collectionDto.NewAndChangeCollectionDto;
import com.example.collections_backend.dto.collectionDto.ReturnCollectionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class CollectionController {
    private final CollectionService collectionService;

    @GetMapping("collections/{username}")
    public Iterable<CollectionEntity> getCollectionsInfo(@PathVariable(value = "username") String username) {
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
    public ResponseEntity<String> addCollection(
            @ModelAttribute NewAndChangeCollectionDto request) throws IOException {
        return ResponseEntity.ok(collectionService.newCollection(request));
    }

    @PostMapping(path ="auth/collection/{idCollection}/edit", consumes = "multipart/form-data")
    public ResponseEntity<String> changeCollection(
            @ModelAttribute NewAndChangeCollectionDto request,
            @PathVariable(value = "idCollection") Long id
    ) throws IOException {
        return ResponseEntity.ok(collectionService.changeCollection(request, id));
    }


}
