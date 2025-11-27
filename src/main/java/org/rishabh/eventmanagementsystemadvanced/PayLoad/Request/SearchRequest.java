package org.rishabh.eventmanagementsystemadvanced.PayLoad.Request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchRequest {

    @Size(max = 100 , message = "Keyword must not exceed 100 characters")
    private String keyword;

    @Size(max = 100 , message = "Venue must not exceed 100 characters")
    private String venue;

    @Size(max = 50 , message = "Category must not exceed 50 characters")
    private String category;

    @FutureOrPresent(message = "Start time must be in the present or future")
    private LocalDateTime startTime;

    @FutureOrPresent(message = "End time must be in the present or future")
    private LocalDateTime endTime;

    @NotNull(message = "Page number is required")
    @Min(value = 0 , message = "Page must be Zero or Positive ")
    private Integer page = 0;

    @NotNull(message = "Page size is required ")
    @Min(value = 1 ,message = "Page size must be at least 1 ")
    private Integer pageSize = 10;
}
