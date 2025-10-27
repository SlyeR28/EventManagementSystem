package org.rishabh.eventmanagementsystemadvanced.Repository;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event,Long>{
    List<Event> findByOrganizer_Id(Long organizerId);

    List<Event> findByCategory_Id(Long categoryId);


}
