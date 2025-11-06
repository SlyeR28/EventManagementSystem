package org.rishabh.eventmanagementsystemadvanced.Controllers;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.TicketValidationMethod;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ValidationResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.TicketValidationRequest;
import org.rishabh.eventmanagementsystemadvanced.Services.TicketValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ticket-validations")
@RequiredArgsConstructor
public class TicketValidationController {

    private final TicketValidationService ticketValidationService;

    @PostMapping()
    public ResponseEntity<ValidationResponse> validateTicket(@RequestBody TicketValidationRequest ticketValidationRequest) {
        TicketValidationMethod method = ticketValidationRequest.getTicketValidationMethod();
        ValidationResponse  ticketValidation;
        if(TicketValidationMethod.MANUAL.equals(method)){
            ticketValidation = ticketValidationService.validateTicketManually(ticketValidationRequest.getId());
        }else {
           ticketValidation =  ticketValidationService.validateTicketByQrCode(ticketValidationRequest.getId());
        }

         return  ResponseEntity.ok(ticketValidation);
    }

}
