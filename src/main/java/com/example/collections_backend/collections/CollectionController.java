package com.example.collections_backend.collections;

import com.example.collections_backend.dto.CollectionAddDto;
import com.example.collections_backend.dto.CollectionReturnDto;
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

    /*@GetMapping("collections/userpage/{username}")
    public Iterable<Collection> getThreeCollectionsInfo(@PathVariable(value = "username") String username) {
        return collectionService.get3CollectionsInfo(username);
    }*/

    @GetMapping("collection/{username}/{idCollection}")
    public CollectionReturnDto getCollectionInfo(
            @PathVariable(value = "idCollection") Long id,
            @PathVariable(value = "username") String username
    ) {
        return collectionService.getCollectionInfo(id, username);
    }

    @PostMapping(path ="auth/collection/new", consumes = "multipart/form-data")
    public ResponseEntity<String> addCollection(@ModelAttribute CollectionAddDto request) throws IOException {
        return ResponseEntity.ok(collectionService.newCollection(request));
    } // realized


    /*@DeleteMapping("auth/collection/{idCollection}/remove")
    public ResponseEntity<String> removeCollection(@PathVariable(value = "idCollection") Long id) {

        return ResponseEntity.ok(collectionService.removeCollection(id));
    }*/

    /*@PutMapping("auth/collection/{idCollection}/edit")
    public ResponseEntity<String> editCollection(
            @PathVariable(value = "idCollection") Long id,
            @RequestBody CollectionAddDto request
    ) {
        System.out.println(request);
        return ResponseEntity.ok(collectionService.editCollection(id, request));
    }*/

}
