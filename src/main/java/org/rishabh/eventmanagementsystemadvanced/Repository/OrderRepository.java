package org.rishabh.eventmanagementsystemadvanced.Repository;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}