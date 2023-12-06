package com.example.collections_backend.search;

import com.example.collections_backend.collections.CollectionRepository;
import com.example.collections_backend.dto.searchDto.SearchItemDto;
import com.example.collections_backend.dto.searchDto.SearchUserCollectionDto;
import com.example.collections_backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final CollectionRepository collectionRepository;
    private final UserRepository userRepository;

    public List<SearchItemDto> findCollectionsByRequest(String request, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        var list =
                collectionRepository.searchCollectionsEntitiesByRequest(request, pageable);
        return list.stream().map(c -> SearchItemDto
                        .builder()
                        .idName(c.getUser().getNickname() + "/" + c.getIdCollection().toString())
                        .image(c.getImage())
                        .title(c.getName())
                        .build())
                .collect(Collectors.toList());
    }

    public List<SearchItemDto> findUsersByRequest(String request, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        var list = userRepository.searchUsersByRequest(request, pageable);
        return list.stream().map(user -> SearchItemDto
                        .builder()
                        .idName(user.getNickname())
                        .image(user.getImage())
                        .title(user.getName() + " " + user.getSurname())
                        .build())
                .collect(Collectors.toList());
    }

    public SearchUserCollectionDto findByRequest(String request) {
        return SearchUserCollectionDto.builder()
                .collections(findCollectionsByRequest(request, 0, 4))
                .collectionsLength(collectionRepository.countCollectionsEntitiesByRequest(request))
                .users(findUsersByRequest(request, 0, 4))
                .usersLength(userRepository.countUsersByRequest(request))
                .build();
    }
}
