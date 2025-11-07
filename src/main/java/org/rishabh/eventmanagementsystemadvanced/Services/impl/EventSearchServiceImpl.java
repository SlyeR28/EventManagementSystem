package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Event;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.EventDocument;
import org.rishabh.eventmanagementsystemadvanced.Mapper.EventMapper;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.EventSearchResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.SearchRequest;

import org.rishabh.eventmanagementsystemadvanced.Repository.EventRepository;
import org.rishabh.eventmanagementsystemadvanced.Repository.EventSearchRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.EventSearchService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.elasticsearch.index.query.BoolQueryBuilder;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


import static org.springframework.data.elasticsearch.client.elc.Queries.matchQuery;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventSearchServiceImpl implements EventSearchService {

    private final EventSearchRepository eventSearchRepository;
    private final ElasticsearchOperations elasticsearchOperations;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;


    @Override
    public void indexEvent(EventSearchResponse response) {
        EventDocument doc = EventDocument.builder()
                .id(response.getId())
                .name(response.getName())
                .venue(response.getVenue())
                .description(response.getDescription())
                .categoryName(response.getCategoryName())
                .organizerName(response.getOrganizerName())
                .imageUrls(response.getImageUrls())
                .startTime(response.getStartTime())
                .endTime(response.getEndTime())
                .build();

        eventSearchRepository.save(doc);

    }


    @Override
    public void deleteIndexedEvent(String eventId) {
        eventSearchRepository.deleteById(eventId);
    }


    @Override
    public void reindexAllEvents() {
        List<Event> allEvents = eventRepository.findAll();
        List<EventDocument> documents = allEvents.stream()
                .map(event -> EventDocument.builder()
                        .id(String.valueOf(event.getId()))
                        .name(event.getName())
                        .venue(event.getVenue())
                        .description(event.getDescription())
                        .categoryName(event.getCategory().getName())
                        .organizerName(event.getOrganizer().getFullName())
                        .startTime(event.getStartTime())
                        .endTime(event.getEndTime())
                        .build())
                .collect(Collectors.toList());

        eventSearchRepository.saveAll(documents);

    }


    @Override
    public List<EventSearchResponse> searchEvents(SearchRequest request) {
        BoolQueryBuilder boolQueryBuilder

        // Combine possible filters
        queryBuilder.withQuery(boolQuery()
                .should(matchQuery("name", request.getKeyword()).fuzziness("AUTO"))
                .should(matchQuery("venue", request.getVenue()).fuzziness("AUTO"))
                .should(matchQuery("categoryName", request.getCategory()).fuzziness("AUTO"))
                .should(matchQuery("organizerName", request.getOrganizer()).fuzziness("AUTO"))
        );

        SearchHits<EventDocument> hits = elasticsearchOperations.search(queryBuilder.build(), EventDocument.class);
        return hits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ✅ Paginated search
    @Override
    public List<EventSearchResponse> searchEventsWithPagination(SearchRequest request, int page, int size, String sortBy) {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(request.getKeyword(), "name", "venue", "categoryName", "description"))
                .withPageable(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy)))
                .build();

        SearchHits<EventDocument> hits = elasticsearchOperations.search(query, EventDocument.class);
        return hits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


    @Override
    public List<EventSearchResponse> fuzzySearch(String keyword) {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(fuzzyQuery("name", keyword))
                .build();

        SearchHits<EventDocument> hits = elasticsearchOperations.search(query, EventDocument.class);
        return hits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


    private EventSearchResponse toResponse(EventDocument doc) {
        return EventSearchResponse.builder()
                .id(doc.getId())
                .name(doc.getName())
                .venue(doc.getVenue())
                .description(doc.getDescription())
                .categoryName(doc.getCategoryName())
                .organizerName(doc.getOrganizerName())
                .imageUrls(doc.getImageUrls())
                .startTime(doc.getStartTime())
                .endTime(doc.getEndTime())
                .build();
    }
}
