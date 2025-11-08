//package org.rishabh.eventmanagementsystemadvanced.Services.impl;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
//
//import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Event;
//import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.EventDocument;
//import org.rishabh.eventmanagementsystemadvanced.Mapper.EventMapper;
//import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.EventSearchResponse;
//import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.SearchRequest;
//import org.rishabh.eventmanagementsystemadvanced.Repository.EventRepository;
//import org.rishabh.eventmanagementsystemadvanced.Repository.EventSearchRepository;
//import org.rishabh.eventmanagementsystemadvanced.Services.EventSearchService;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class EventSearchServiceImpl implements EventSearchService {
//
//
//    private final EventSearchRepository eventSearchRepository;
//    private final ElasticsearchOperations elasticsearchOperations;
//    private final EventRepository eventRepository;
//    private final EventMapper eventMapper;
//    private final ElasticsearchTemplate  elasticsearchTemplate;
//
//    @Override
//    public void indexEvent(EventSearchResponse response) {
//        EventDocument doc = EventDocument.builder()
//                .id(response.getId())
//                .name(response.getName())
//                .venue(response.getVenue())
//                .description(response.getDescription())
//                .categoryName(response.getCategoryName())
//                .organizerName(response.getOrganizerName())
//                .imageUrls(response.getImageUrls())
//                .startTime(response.getStartTime())
//                .endTime(response.getEndTime())
//                .build();
//
//        eventSearchRepository.save(doc);
//
//    }
//
//
//    @Override
//    public void deleteIndexedEvent(String eventId) {
//        eventSearchRepository.deleteById(eventId);
//    }
//
//
//    @Override
//    public void reindexAllEvents() {
//        List<Event> allEvents = eventRepository.findAll();
//        List<EventDocument> documents = allEvents.stream()
//                .map(event -> EventDocument.builder()
//                        .id(String.valueOf(event.getId()))
//                        .name(event.getName())
//                        .venue(event.getVenue())
//                        .description(event.getDescription())
//                        .categoryName(event.getCategory().getName())
//                        .organizerName(event.getOrganizer().getFullName())
//                        .startTime(event.getStartTime())
//                        .endTime(event.getEndTime())
//                        .build())
//                .collect(Collectors.toList());
//
//        eventSearchRepository.saveAll(documents);
//    }
//
//
//    @Override
//    public List<EventSearchResponse> searchEvents(SearchRequest request) {
//        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//
//        if(request.getKeyword() != null && !request.getKeyword().isEmpty()) {
//            boolQuery.should(QueryBuilders.matchQuery("name", request.getKeyword()))
//                    .should(QueryBuilders.matchQuery("venue", request.getVenue()))
//                    .should(QueryBuilders.matchQuery("category", request.getCategory()))
//                    .should(QueryBuilders.matchQuery("organizer", request.getOrganizer()));
//        }
//        if(request.getCategory() != null && !request.getCategory().isEmpty()) {
//            boolQuery.should(QueryBuilders.matchQuery("category", request.getCategory()));
//        }
//        if(request.getOrganizer() != null && !request.getOrganizer().isEmpty()) {
//            boolQuery.should(QueryBuilders.matchQuery("organizer", request.getOrganizer()));
//        }
//        if (request.getVenue() != null && !request.getVenue().isEmpty()) {
//            boolQuery.should(QueryBuilders.matchQuery("venue", request.getVenue()));
//        }
//
//
//
//
//    }
//
//
//    @Override
//    public List<EventSearchResponse> searchEventsWithPagination(SearchRequest request, int page, int size, String sortBy) {
//
//    }
//
//
//    @Override
//    public List<EventSearchResponse> fuzzySearch(String keyword) {
//
//    }
//
//
//    private EventSearchResponse toResponse(EventDocument doc) {
//        return EventSearchResponse.builder()
//                .id(doc.getId())
//                .name(doc.getName())
//                .venue(doc.getVenue())
//                .description(doc.getDescription())
//                .categoryName(doc.getCategoryName())
//                .organizerName(doc.getOrganizerName())
//                .imageUrls(doc.getImageUrls())
//                .startTime(doc.getStartTime())
//                .endTime(doc.getEndTime())
//                .build();
//    }
//}
