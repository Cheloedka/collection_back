package com.example.collections_backend.search;

import com.example.collections_backend.dto.searchDto.SearchItemDto;
import com.example.collections_backend.dto.searchDto.SearchUserCollectionDto;
import com.example.collections_backend.exception_handling.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private static final String DEFAULT_PAGE_SIZE = "20";

    private final SearchService searchService;

    @GetMapping("/{request}")
    public SearchUserCollectionDto find(@PathVariable String request) {
        return searchService.findByRequest(request);
    }

    @GetMapping("/by_type/{type}/{request}")
    public List<SearchItemDto>
    findByType(@PathVariable String request,
               @PathVariable String type,
               @RequestParam(defaultValue = "0", required = false) int page,
               @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize
    ) {

        if (type.equals("collection"))
            return searchService.findCollectionsByRequest(request, page, pageSize);
        if (type.equals("user"))
            return searchService.findUsersByRequest(request, page, pageSize);

        throw new BadRequestException("There is not such search type as \"" + type + "\" ");
    }

}
