package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.OrderItem;
import org.rishabh.eventmanagementsystemadvanced.Mapper.OrderItemMapper;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.OrderItemResponse;
import org.rishabh.eventmanagementsystemadvanced.Repository.OrderItemRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.OrderItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItemResponse> getItemsByEventId(Long eventId) {
        // ✅ Fetch with event & ticketType eagerly to avoid LazyInitializationException
        List<OrderItem> items = orderItemRepository.findByEventIdWithRelations(eventId);

        // ✅ Convert to response using mapper
        return items.stream()
                .map(orderItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderItemResponse> getItemsByOrderId(Long orderId) {
        List<OrderItem> items = orderItemRepository.findByOrderIdWithRelations(orderId);

        return items.stream()
                .map(orderItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void saveAll(List<OrderItem> orderItemList) {
        orderItemRepository.saveAll(orderItemList);
    }

    @Transactional
    @Override
    public void deleteByOrderId(Long orderId) {
        orderItemRepository.deleteByOrderId(orderId);
    }
}
