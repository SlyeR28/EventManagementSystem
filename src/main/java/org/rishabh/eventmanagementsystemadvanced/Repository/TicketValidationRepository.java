package org.rishabh.eventmanagementsystemadvanced.Repository;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.TicketValidation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketValidationRepository extends JpaRepository<TicketValidation, Long> {
}