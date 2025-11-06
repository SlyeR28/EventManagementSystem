package org.rishabh.eventmanagementsystemadvanced.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.TicketValidation;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ValidationResponse;

@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketValidationMapper {

    @Mapping(target = "ticketId", source = "ticket.id")
    ValidationResponse toTicketValidationResponseDto(TicketValidation ticketValidation);
}
