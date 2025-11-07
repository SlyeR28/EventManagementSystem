package org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class EventSearchResponse {
    private String id;
    private String name;
    private String venue;
    private String description;
    private String categoryName;
    private String organizerName;
    private List<String> imageUrls;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
