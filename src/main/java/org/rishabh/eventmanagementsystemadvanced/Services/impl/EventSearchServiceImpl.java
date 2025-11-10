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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventSearchServiceImpl implements EventSearchService {


    private final EventSearchRepository eventSearchRepository;
    private final EventRepository eventRepository;



    private static final List<String> EVENT_STATUS = Arrays.asList("PUBLISHED", "ONGOING");



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
    public PagedResponse<EventSearchResponse> searchEvents(SearchRequest request) {


        PageRequest pageable  = PageRequest.of(request.getPage(), request.getPageSize());
        String keyword = request.getKeyword() != null ? request.getKeyword() : " ";

        Page<EventDocument> eventPage = eventSearchRepository.
                findByStatusInAndNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        EVENT_STATUS , keyword  , keyword ,pageable
                );
        List<EventSearchResponse> content = eventPage.getContent()
                .stream()
                .map(this::convertToEventDocument)
                .collect(Collectors.toList());


        return new PagedResponse<>(
                content,
                eventPage.getNumber(),
                eventPage.getSize(),
                eventPage.getTotalElements(),
                eventPage.getTotalPages(),
                eventPage.isLast()
        );
    }



    @Override
    public List<EventSearchResponse> fuzzySearch(String keyword) {
        if(keyword == null || keyword.isEmpty()) {return List.of();}

        List<EventDocument> eventDocs = eventSearchRepository
                .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword , keyword);

        return eventDocs.stream().map(this::convertToEventDocument).collect(Collectors.toList());
    }




    // helper method to convert to response
    private EventSearchResponse convertToEventDocument(EventDocument eventDocument) {
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
                        .startTime(LocalDate.from(event.getStartTime()))
                        .endTime(LocalDate.from(event.getEndTime()))
                        .categoryName(event.getCategory() != null ? event.getCategory().getName() : null)
                        .ticketTypes(tickets)
                        .images(images)
                        .build();

    }


}
