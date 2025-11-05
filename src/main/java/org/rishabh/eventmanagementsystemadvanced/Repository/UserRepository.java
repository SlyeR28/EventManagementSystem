package org.rishabh.eventmanagementsystemadvanced.Repository;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.User;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByActivationCode(String activationCode);
    Page<User>findByRole(Role role, Pageable pageable);

    Page<User> findByRoleIn(List<String> roles, Pageable pageable);


    @Query("SELECT u FROM User u WHERE u.id NOT IN (SELECT t.id FROM Ticket t WHERE t.id = :eventId)")
    List<User> findAllUsersWithoutTickets(@Param("eventId") Long eventId);

    @Query("SELECT DISTINCT u FROM User u WHERE u.id IN " +
            "(SELECT t.id FROM Ticket t WHERE t.id = :eventId) " +
            "OR u.id IN (SELECT c.user.id FROM Cart c WHERE c.id = :eventId)")
    List<User> findAllUsersWithTicketsOrCart(@Param("eventId") Long eventId);

    @Query("SELECT DISTINCT u FROM User u WHERE u.id IN " +
            "(SELECT c.user.id FROM Cart c WHERE c.id = :eventId)")
    List<User> findUsersWithTicketsInCart(@Param("eventId") Long eventId);

}
