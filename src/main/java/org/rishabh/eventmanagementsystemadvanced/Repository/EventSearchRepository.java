package org.rishabh.eventmanagementsystemadvanced.Repository;


import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.EventDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventSearchRepository extends ElasticsearchRepository<EventDocument, String> {

    Page<EventDocument> findByOrganizerNameContainingIgnoreCase(String organizerName ,  Pageable pageable);

    Page<EventDocument> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<EventDocument> findByVenueContainingIgnoreCase(String venue, Pageable pageable);

    Page<EventDocument> findByCategoryContainingIgnoreCase(String category, Pageable pageable);

}
