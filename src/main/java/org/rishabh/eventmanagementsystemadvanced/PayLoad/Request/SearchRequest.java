package org.rishabh.eventmanagementsystemadvanced.PayLoad.Request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchRequest {

    private String keyword;
    private String venue;
    private String category;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer page = 0;
    private Integer pageSize = 10;
}
