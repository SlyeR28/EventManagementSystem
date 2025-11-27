package org.rishabh.eventmanagementsystemadvanced.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Ticket;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.TicketResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.TicketRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {

    // Entity -> Response DTO
    @Mapping(target = "ticketTypeName", source = "ticketType.name")
    @Mapping(target = "eventName", source = "ticketType.event.name")
    @Mapping(target = "purchaserName", source = "purchaser.fullName")
    @Mapping(target = "priceAtPurchase", source = "priceAtPurchase")
    @Mapping(target = "purchasedAt", source = "createdAt")
    TicketResponse toResponse(Ticket ticket);

    // Request DTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ticketType", ignore = true)
    @Mapping(target = "purchaser", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "qrCodeUrl", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Ticket toEntity(TicketRequest request);
}
