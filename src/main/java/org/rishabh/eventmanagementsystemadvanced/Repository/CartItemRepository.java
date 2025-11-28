package org.rishabh.eventmanagementsystemadvanced.Repository;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByIdAndCartUserId(Long id, Long userId);

}
