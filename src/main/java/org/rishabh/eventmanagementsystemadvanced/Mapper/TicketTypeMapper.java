package org.rishabh.eventmanagementsystemadvanced.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.TicketType;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.TicketTypeDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.TicketTypeRequest;

@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketTypeMapper {


    TicketTypeMapper INSTANCE = Mappers.getMapper(TicketTypeMapper.class);

    // Request DTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "event", ignore = true) // Will be set in service
    @Mapping(target = "tickets", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    TicketType toEntity(TicketTypeRequest request);

    // Entity -> Response DTO
    @Mapping(target = "eventId", source = "event.id")
    TicketTypeDto toResponse(TicketType ticketType);
}
