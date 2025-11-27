package org.rishabh.eventmanagementsystemadvanced.PayLoad.Request;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesTimeRequest {
    @FutureOrPresent(message = "Start time must be in the present or future")
    private LocalDateTime salesStartTime;

    @FutureOrPresent(message = "End time must be in the present or future")
    private LocalDateTime salesEndTime;
}