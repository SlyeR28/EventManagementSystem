package org.rishabh.eventmanagementsystemadvanced.Repository;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Images,Long> {


    Optional<Images> findByPublicId(String publicId);


    Optional<Images> findByUser_Id(Long userId);


    List<Images> findAllByEvent_Id(Long eventId);
}
