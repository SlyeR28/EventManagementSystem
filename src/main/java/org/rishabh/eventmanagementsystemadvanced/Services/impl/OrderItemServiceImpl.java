package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.OrderItem;
import org.rishabh.eventmanagementsystemadvanced.Repository.OrderItemRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.OrderItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;


    @Override
    public List<OrderItem> getItemsByEventId(Long eventId) {
        return orderItemRepository.findByEventId(eventId);
    }

    @Override
    public List<OrderItem> getItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    @Override
    public void saveAll(List<OrderItem> orderItemList) {
          orderItemRepository.saveAll(orderItemList);
    }



    @Override
    public void deleteByOrderId(Long orderId) {
        orderItemRepository.deleteByOrderId(orderId);

    }
}
