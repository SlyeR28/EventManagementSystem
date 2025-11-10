package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Event;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.EventStatus;
import org.rishabh.eventmanagementsystemadvanced.Repository.EventRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.EventAutoUpdateService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventAutoUpdateServiceImpl implements EventAutoUpdateService {


    private final EventRepository eventRepository;

    @Override
    public void updateEventStatus(Event event) {
        LocalDateTime now = LocalDateTime.now();
        if (event.getStatus() == EventStatus.PUBLISHED) {
            if (now.isAfter(event.getStartTime()) && now.isBefore(event.getEndTime())) {
                event.setStatus(EventStatus.ONGOING);

            } else if (now.isAfter(event.getEndTime())) {
                event.setStatus(EventStatus.COMPLETED);

            }
            // Save if a status change occurred
            eventRepository.save(event);
        }
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    @Transactional
    @Override
    public void autoUpdateEventStatuses() {

        LocalDateTime now = LocalDateTime.now();

        // 1. Find all events that might need an update (e.g., all PUBLISHED, ONGOING, and future DRAFTs)
        // A simple query to fetch all published events is a good start.
        List<Event> eventsToUpdate = eventRepository.findAllByStatusIn(
                List.of(EventStatus.PUBLISHED, EventStatus.ONGOING)
        );

        for (Event event : eventsToUpdate) {

            // Check if published event should become ONGOING or COMPLETED
            if (event.getStatus() == EventStatus.PUBLISHED) {
                if (now.isAfter(event.getStartTime())) { // It's time to start or complete
                    if (now.isBefore(event.getEndTime())) {
                        event.setStatus(EventStatus.ONGOING);
                    } else {
                        event.setStatus(EventStatus.COMPLETED);
                    }
                    eventRepository.save(event);
                }
            }

            // Check if ongoing event should become COMPLETED
            else if (event.getStatus() == EventStatus.ONGOING) {
                if (now.isAfter(event.getEndTime())) {
                    event.setStatus(EventStatus.COMPLETED);
                    eventRepository.save(event);
                }
            }
        }
    }

}
