package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.SearchRequest;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.EventSearchResponse;

import java.util.List;

public interface EventSearchService {


    void indexEvent(EventSearchResponse response);


    void deleteIndexedEvent(String eventId);


    void reindexAllEvents();


    List<EventSearchResponse> searchEvents(SearchRequest request);


    List<EventSearchResponse> searchEventsWithPagination(SearchRequest request, int page, int size, String sortBy);


    List<EventSearchResponse> fuzzySearch(String keyword);
}
