package org.rishabh.eventmanagementsystemadvanced.Controllers;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PagedResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.EventSearchResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.SearchRequest;
import org.rishabh.eventmanagementsystemadvanced.Services.EventSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventSearchController {

    private final EventSearchService eventSearchService;

    // ------------------------- Search Events with Pagination -------------------------
    @PostMapping("/search")
    public ResponseEntity<PagedResponse<EventSearchResponse>> searchEvents(@RequestBody SearchRequest request) {
        PagedResponse<EventSearchResponse> response = eventSearchService.searchEvents(request);
        return ResponseEntity.ok(response);
    }

    // ------------------------- Fuzzy Search -------------------------
    @GetMapping("/fuzzy")
    public ResponseEntity<List<EventSearchResponse>> fuzzySearch(@RequestParam("keyword") String keyword) {
        List<EventSearchResponse> results = eventSearchService.fuzzySearch(keyword);
        return ResponseEntity.ok(results);
    }

    // ------------------------- Index a Single Event -------------------------
    @PostMapping("/index/{eventId}")
    public ResponseEntity<String> indexEvent(@PathVariable Long eventId) {
        eventSearchService.indexEvent(eventId);
        return ResponseEntity.ok("Event indexed successfully");
    }

    // ------------------------- Delete Indexed Event -------------------------
    @DeleteMapping("/index/{eventId}")
    public ResponseEntity<String> deleteIndexedEvent(@PathVariable Long eventId) {
        eventSearchService.deleteIndexedEvent(eventId);
        return ResponseEntity.ok("Event removed from index successfully");
    }

    // ------------------------- Reindex All Events -------------------------
    @PostMapping("/reindex")
    public ResponseEntity<String> reindexAllEvents() {
        eventSearchService.reindexAllEvents();
        return ResponseEntity.ok("All events reindexed successfully");
    }
}
