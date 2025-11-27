package org.rishabh.eventmanagementsystemadvanced.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Event;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.EventDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.EventRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    // Convert EventRequest to Event entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", expression = "java(org.rishabh.eventmanagementsystemadvanced.Domains.Modal.EventStatus.DRAFT)")
    @Mapping(target = "organizer", ignore = true) // We'll set it in service
    @Mapping(target = "category", ignore = true)// We'll set it in service
    @Mapping(target = "ticketTypes", ignore = true) // set in service
    @Mapping(target = "attendees", ignore = true)
    @Mapping(target = "staff", ignore = true)
    @Mapping(target = "images", ignore = true)
    Event toEntity(EventRequest request);

    // Convert Event entity to EventResponse DTO
    @Mapping(target = "organizerId", source = "organizer.id")
    @Mapping(target = "categoryId", source = "category.id")
    EventDto toDto(Event event);

}
