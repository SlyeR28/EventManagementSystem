package org.rishabh.eventmanagementsystemadvanced.Repository;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.OrderItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {


    void deleteByOrderId(Long orderId);

    @Query("""
        SELECT oi FROM OrderItem oi
        JOIN FETCH oi.event e
        JOIN FETCH oi.ticketType t
        WHERE e.id = :eventId
    """)
    List<OrderItem> findByEventIdWithRelations(@Param("eventId") Long eventId);

    @Query("""
        SELECT oi FROM OrderItem oi
        JOIN FETCH oi.event e
        JOIN FETCH oi.ticketType t
        WHERE oi.order.id = :orderId
    """)
    List<OrderItem> findByOrderIdWithRelations(Long orderId);



}