package org.rishabh.eventmanagementsystemadvanced.Mapper;

import org.mapstruct.*;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Cart;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.CartItem;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.CartItemResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.CartResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {

    // Main mapping
    @Mapping(target = "cartId", source = "id")
    @Mapping(target = "items", source = "items")
    @Mapping(target = "totalPrice", source = "totalPrice")
    CartResponse toCartResponse(Cart cart);

    // Map individual items
    @Mapping(target = "cartItemId", source = "id")
    @Mapping(target = "eventId", source = "ticketType.event.id")
    @Mapping(target = "eventName", source = "ticketType.event.name")
    @Mapping(target = "ticketTypeId", source = "ticketType.id")
    @Mapping(target = "ticketTypeName", source = "ticketType.name")
    @Mapping(target = "ticketPrice", source = "ticketType.currentPrice")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "totalPrice", source = "price")
    CartItemResponse toCartItemResponse(CartItem item);

    // Map list of items
    List<CartItemResponse> toCartItemResponseList(List<CartItem> items);
}
