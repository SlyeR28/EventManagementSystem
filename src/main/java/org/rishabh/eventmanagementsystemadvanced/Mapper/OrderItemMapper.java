package org.rishabh.eventmanagementsystemadvanced.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.OrderItem;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.OrderItemResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.OrderItemRequest;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    // Entity -> Response
    @Mapping(source = "event.name", target = "eventName")
    @Mapping(source = "ticketType.name", target = "ticketTypeName")
    OrderItemResponse toResponse(OrderItem item);

    // Request -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true) // handled in service
    @Mapping(target = "event.id", source = "eventId")
    @Mapping(target = "ticketType.id", source = "ticketTypeId")
    OrderItem toEntity(OrderItemRequest request);
}
