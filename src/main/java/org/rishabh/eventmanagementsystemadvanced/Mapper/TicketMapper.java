package org.rishabh.eventmanagementsystemadvanced.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Ticket;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.TicketResponse;


@Mapper(componentModel = "spring")
public interface TicketMapper {

    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    @Mapping(source = "id", target = "ticketId")
    @Mapping(source = "ticketType.name", target = "ticketTypeName")
    @Mapping(source = "purchaser.fullName", target = "purchaserName")
    @Mapping(source = "status", target = "status")
    TicketResponse toResponse(Ticket ticket);
}
