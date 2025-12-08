package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Event;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.EventDocument;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.ImageDocument;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.TicketTypeDocument;
import org.rishabh.eventmanagementsystemadvanced.Exception.EventNotFoundException;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.EventSearchResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PagedResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.SearchRequest;
import org.rishabh.eventmanagementsystemadvanced.Repository.EventRepository;
import org.rishabh.eventmanagementsystemadvanced.Repository.EventSearchRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.EventSearchService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventSearchServiceImpl implements EventSearchService {


    private final EventSearchRepository eventSearchRepository;
    private final EventRepository eventRepository;
    private final ElasticsearchOperations  elasticsearchOperations;



    private static final List<String> PUBLIC_STATUS  = Arrays.asList("PUBLISHED", "ONGOING");



    @Transactional
    @Override
    @CacheEvict(value = "events", allEntries = true)
    public void indexEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new EventNotFoundException("Event not found with id " + eventId)
        );
        EventDocument doc = convertToEventDocument(event);
        eventSearchRepository.save(doc);


    }

    @Override
    @CacheEvict(value = "events", allEntries = true)
    public void deleteIndexedEvent(Long eventId) {
         eventSearchRepository.deleteById(eventId);

    }

    @Transactional(readOnly = true)
    @Override
    @CacheEvict(value = "events", allEntries = true)
    public void reindexAllEvents() {
        List<Event> events = eventRepository.findAll();
        List<EventDocument>docs = events.stream()
                .map(this::convertToEventDocument)
                .collect(Collectors.toList());
        eventSearchRepository.saveAll(docs);

    }

    @Override
    @Cacheable(value = "events", key = "#request.page + '-' + #request.pageSize +" +
            " '-' + (#request.keyword ?: '') + '-' + (#request.venue ?: '') + '-' + (#request.category ?: '')")
    public PagedResponse<EventSearchResponse> searchEvents(SearchRequest request , Authentication authentication) {
        Criteria criteria = new Criteria();

      // 1. Role- based status filtering
        boolean isAdminOrganiser = false;
        if(authentication != null){
            isAdminOrganiser = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN") ||  role.equals("ROLE_ORGANISER"));
        }
        if(isAdminOrganiser){
            // Admin/Organizer can search by specific status if provided , otherwise all
            if(request.getStatus() != null && !request.getStatus().isEmpty()){
              criteria  = criteria.and("status").is(request.getStatus());
            }else{
                // Public/ user : Force PUBLISHED or ONGOING
                // if they request a specific status , check if it's allowed
                if(request.getStatus() != null && !request.getStatus().isEmpty()){
                    if (PUBLIC_STATUS.contains(request.getStatus())) {
                        criteria  = criteria.and("status").is(request.getStatus());
                    }else {
                        // if they ask for DRAFT/CANCELLED , return empty or force allowed statuses
                        // Here we force allowed Statuses , effectively ignoring their invalid request or
                        // return nothing ?
                        // lets force allowed statuses to be Safe
                        criteria  = criteria.and("status").in(PUBLIC_STATUS);
                    }
                }else {
                    criteria  = criteria.and("status").in(PUBLIC_STATUS);
                }
            }
            // 2. Keyword Search (Name or Description)
            if (request.getKeyword() != null && !request.getKeyword().trim().isEmpty()) {
                criteria = criteria.and(new Criteria("name").contains(request.getKeyword())
                        .or("description").contains(request.getKeyword()));
            }

            // 3. Venue
            if (request.getVenue() != null && !request.getVenue().isEmpty()) {
                criteria = criteria.and("venue").is(request.getVenue());
            }

            // 4. Category
            if (request.getCategory() != null && !request.getCategory().isEmpty()) {
                criteria = criteria.and("categoryName").is(request.getCategory());
            }

            // 5. Date Range
            if (request.getStartTime() != null) {
                criteria = criteria.and("startTime").greaterThanEqual(request.getStartTime());
            }
            if (request.getEndTime() != null) {
                criteria = criteria.and("endTime").lessThanEqual(request.getEndTime());
            }
        }


        PageRequest pageable = PageRequest.of(request.getPage(), request.getPageSize());
        CriteriaQuery query = new CriteriaQuery(criteria).setPageable(pageable);

        SearchHits<EventDocument> searchHits = elasticsearchOperations.search(query, EventDocument.class);

        List<EventSearchResponse> content = searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return new PagedResponse<>(
                content,
                request.getPage(),
                request.getPageSize(),
                searchHits.getTotalHits(),
                (int) Math.ceil((double) searchHits.getTotalHits() / request.getPageSize()),
                (long) (request.getPage() + 1) * request.getPageSize() >= searchHits.getTotalHits());
    }




    @Override
    public List<EventSearchResponse> fuzzySearch(String keyword) {
        if(keyword == null || keyword.isEmpty()) {return List.of();}
        // Using criteria for fuzzy search or name or description
        Criteria criteria = new Criteria().fuzzy(keyword)
                .or(("description")).fuzzy(keyword);
        CriteriaQuery query = new CriteriaQuery(criteria);
        SearchHits<EventDocument> searchHits = elasticsearchOperations.search(query, EventDocument.class);

        return searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }




    // helper method to convert to response
    private EventSearchResponse convertToResponse(EventDocument eventDocument) {
        return EventSearchResponse.builder()
                .id(eventDocument.getId())
                .name(eventDocument.getName())
                .description(eventDocument.getDescription())
                .venue(eventDocument.getVenue())
                .startTime(eventDocument.getStartTime())
                .endTime(eventDocument.getEndTime())
                .tickets(eventDocument.getTicketTypes() != null ?
                        eventDocument.getTicketTypes().stream()
                                .map(t-> EventSearchResponse.TicketInfo.builder()
                                        .ticketId(t.getTicketTypeId())
                                        .name(t.getName())
                                        .currentPrice(t.getCurrentPrice())
                                        .remainingQuantity(t.getRemainingQuantity())
                                        .build())
                                .collect(Collectors.toList()): null)

                .imageInfos(eventDocument.getImages() != null ? eventDocument.getImages()
                        .stream()
                        .map(i -> EventSearchResponse.ImageElkInfo.builder()
                                .securedUrl(i.getSecuredUrl())
                                .publicId(i.getPublicId())
                                .folder(i.getFolder())
                                .format(i.getFormat())
                                .build())
                                .collect(Collectors.toList())
                        : null)
                .build();
    }



    // helper method to converting event to event document
    private EventDocument convertToEventDocument(Event event) {
        List<TicketTypeDocument> tickets = event.getTicketTypes()
                .stream()
                .map(ti -> TicketTypeDocument.builder()
                        .ticketTypeId(ti.getId())
                        .name(ti.getName())
                        .basePrice(ti.getBasePrice())
                        .currentPrice(ti.getCurrentPrice())
                        .totalQuantity(ti.getTotalQuantity())
                        .remainingQuantity(ti.getRemainingQuantity())
                        .build()).collect(Collectors.toList());
                List<ImageDocument>images = event.getImages()
                        .stream()
                        .map(i -> ImageDocument.builder()
                                .securedUrl(i.getSecuredUrl())
                                .publicId(i.getPublicId())
                                .folder(i.getFolder())
                                .format(i.getFormat())
                                .build())
                        .collect(Collectors.toList());

                return EventDocument.builder()
                        .id(event.getId())
                        .name(event.getName())
                        .description(event.getDescription())
                        .venue(event.getVenue())
                        .status(event.getStatus().name())
                        .startTime(event.getStartTime())
                        .endTime(event.getEndTime())
                        .categoryName(event.getCategory() != null ? event.getCategory().getName() : null)
                        .ticketTypes(tickets)
                        .images(images)
                        .build();

    }


}
