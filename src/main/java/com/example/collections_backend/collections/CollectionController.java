package com.example.collections_backend.collections;

import com.example.collections_backend.dto.ChangeBackDto;
import com.example.collections_backend.dto.collectionDto.NewAndChangeCollectionDto;
import com.example.collections_backend.dto.collectionDto.ReturnCollectionDto;
import com.example.collections_backend.dto.collectionDto.RightInfoInCollectionAndItemPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class CollectionController {

    private final CollectionService collectionService;

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

    @PutMapping(path ="auth/collection/{idCollection}/back", consumes = "multipart/form-data")
    public ResponseEntity<String> editBackCollection(
            @PathVariable Long idCollection,
            @ModelAttribute ChangeBackDto request
    ) throws IOException {
        return ResponseEntity.ok(collectionService.changeBackgroundImage(request.getFile(), idCollection));
    }

    @GetMapping("collections/{username}")
    public List<ReturnCollectionDto> getCollectionsInfo(@PathVariable(value = "username") String username) {
        return collectionService.getCollectionsInfo(username);
    }

    @GetMapping("collection/getTop3/{username}")
    public List<ReturnCollectionDto> getTop3Collections(@PathVariable(value = "username") String username) {
        return collectionService.getTop3Collections(username);
    }

    @GetMapping("collection/{username}/{idCollection}")
    public ReturnCollectionDto getCollectionInfo( @PathVariable(value = "idCollection") Long id,
            @PathVariable(value = "username") String username
    ) {
        return collectionService.getCollectionInfo(id, username);
    }

    @GetMapping("collection/{idCollection}/getRightInfo")
    public RightInfoInCollectionAndItemPageDto getRightInfo(@PathVariable Long idCollection) {
        return collectionService.getRightInfo(idCollection);
    }

    @GetMapping("collection/{idCollection}/isUserOwner")
    public Boolean checkIsUserOwner(@PathVariable Long idCollection) {
        return collectionService.isUserOwner(idCollection);
    }

    @DeleteMapping("auth/collection/delete/{idCollection}")
    public ResponseEntity<String> deleteCollection(@PathVariable Long idCollection) {
        collectionService.deleteCollection(idCollection);
        return ResponseEntity.ok("Success");
    }

}
