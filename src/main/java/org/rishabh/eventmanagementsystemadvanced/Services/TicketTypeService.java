package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.TicketTypeDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.TicketTypeRequest;

import java.util.List;

public interface TicketTypeService {

    TicketTypeDto createTicketType(TicketTypeRequest ticketTypeRequest);
    TicketTypeDto updateTicketType( Long id ,TicketTypeRequest ticketTypeRequest);
    List<TicketTypeDto> getAllTicketTypesByEventId(Long eventId);
    List<TicketTypeDto> getAllTicketTypes();
    TicketTypeDto getTicketTypeById(Long id);
    void deleteTicketType(Long id);
}
