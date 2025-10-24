package org.rishabh.eventmanagementsystemadvanced.Repository;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Images;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Images,Long> {

    Images findByUserId(long id);
    List<Images> findByEventId(long id);

    void deleteByUserId(long id);
    void deleteByEventId(long id);

    Optional<Images> findFirstByUser(User user);
}
