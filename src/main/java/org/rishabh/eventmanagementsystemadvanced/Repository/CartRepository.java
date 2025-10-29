package org.rishabh.eventmanagementsystemadvanced.Repository;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    /**
     * Finds a user's cart by user ID.
     * This is used when adding/viewing/removing items.
     */
    Optional<Cart> findByUserId(Long userId);

    /**
     * Checks if a user already has a cart created.
     */
    boolean existsByUserId(Long userId);
}