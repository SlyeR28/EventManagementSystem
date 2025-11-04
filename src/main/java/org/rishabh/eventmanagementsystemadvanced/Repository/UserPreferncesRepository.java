package org.rishabh.eventmanagementsystemadvanced.Repository;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.UserPrefernces;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPreferncesRepository extends JpaRepository<UserPrefernces, Long> {
    Optional<UserPrefernces> findByUserId(Long userId);
}