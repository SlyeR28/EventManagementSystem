package org.rishabh.eventmanagementsystemadvanced.PayLoad.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.PricingStrategyType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {

    @NotBlank(message = "Event name is required")
    @Size(max = 100, message = "Event name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Venue is required")
    @Size(max = 200, message = "Venue must not exceed 200 characters")
    private String venue;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    private LocalDateTime endTime;

    private PricingStrategyType pricingStrategyType;

    @NotBlank(message = "Status is required")
    private String status; // convert to EventStatus enum in service

    @NotNull(message = "Sales start time is required")
    private LocalDateTime salesStartTime;

    @NotNull(message = "Sales end time is required")
    private LocalDateTime salesEndTime;

    @NotNull(message = "Organizer ID is required")
    private Long organizerId;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Atleast one ticket")
    private List<TicketTypeRequest> ticketTypes = new ArrayList<>();
}
