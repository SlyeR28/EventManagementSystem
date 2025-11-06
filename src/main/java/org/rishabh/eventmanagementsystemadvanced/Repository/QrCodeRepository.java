package org.rishabh.eventmanagementsystemadvanced.Repository;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.QrCode;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.QrCodeStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QrCodeRepository extends JpaRepository<QrCode, Long> {

    Optional<QrCode> findByTicketIdAndTicketPurchaserId(Long ticketId , Long ticketPurchaserId);

    Optional<QrCode> findByIdAndStatus(Long id, QrCodeStatusEnum status);
}
