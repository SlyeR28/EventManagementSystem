package org.rishabh.eventmanagementsystemadvanced.Repository;


import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.EventDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface EventSearchRepository extends ElasticsearchRepository<EventDocument, Long> {

    Page<EventDocument> findByStatusInAndNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            List<String> statuses,
            String nameKeyword,
            String descriptionKeyword,
            Pageable pageable

    );

    List<EventDocument>findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String nameKeyword, String descriptionKeyword);

}
