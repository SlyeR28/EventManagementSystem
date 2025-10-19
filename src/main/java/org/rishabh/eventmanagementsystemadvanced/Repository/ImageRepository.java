package org.rishabh.eventmanagementsystemadvanced.Repository;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Images,Long> {
     List<Images>findAllByEventId(Long eventId);
     Images findByUserId(Long userId);
     Images findByPublicId(Long publicId);
}
