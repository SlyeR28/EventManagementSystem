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
//    Entity → Response
    @Mapping(target = "orderItemId", source = "id") // add this line
    @Mapping(target = "eventName", source = "event.name")
    @Mapping(target = "ticketTypeName", source = "ticketType.name")
    OrderItemResponse toResponse(OrderItem item);

    // Request → Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true) // handled in service
    @Mapping(target = "event", ignore = true) // set manually in service
    @Mapping(target = "ticketType", ignore = true) // set manually in service
    OrderItem toEntity(OrderItemRequest request);
}
