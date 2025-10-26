package org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDto {
    private Long id;
    private String name;
    private String venue;
    private String description;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;

    private LocalDateTime salesStartTime;
    private LocalDateTime salesEndTime;

    private Long organizerId;
    private Long categoryId;

    private List<TicketTypeDto> ticketTypes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
