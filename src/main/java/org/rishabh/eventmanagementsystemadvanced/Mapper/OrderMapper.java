package org.rishabh.eventmanagementsystemadvanced.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Order;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.OrderResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.OrderRequest;

@Mapper(componentModel = "spring" , uses = {OrderItemMapper.class})
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);


    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "userName", source = "user.fullName")
    @Mapping(target = "orderStatus", source = "status")
    @Mapping(target = "items", source = "orderItems")
    OrderResponse toResponse(Order order);

    @Mapping(target = "orderItems", source = "items")
    Order toEntity(OrderRequest request);
}
