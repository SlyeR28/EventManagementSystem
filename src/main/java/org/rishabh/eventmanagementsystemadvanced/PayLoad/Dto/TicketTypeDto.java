package org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketTypeDto {
    private Long id;
    private String name;
    private Double price;
    private Integer totalAvailable;
    private Long eventId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
