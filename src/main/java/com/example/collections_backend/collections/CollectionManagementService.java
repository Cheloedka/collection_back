package com.example.collections_backend.collections;

import com.example.collections_backend.exception_handling.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CollectionManagementService {

    private final CollectionRepository collectionRepository;

    public CollectionEntity findById(Long id) {
        return collectionRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
