package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PagedResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.SearchRequest;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.EventSearchResponse;

import java.util.List;

public interface EventSearchService {


    void indexEvent(Long eventId);


    void deleteIndexedEvent(Long eventId);

    // Re-index all events (useful if ES and MySQL go out of sync)
    void reindexAllEvents();


    PagedResponse<EventSearchResponse> searchEvents(SearchRequest request);



    // Fuzzy search by keyword (optional for auto-suggest)
    List<EventSearchResponse> fuzzySearch(String keyword);
}
