package org.rishabh.eventmanagementsystemadvanced.Repository;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.NotificationTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationTemplateRepository extends MongoRepository<NotificationTemplate,String> {
    List<NotificationTemplate> findByCode(String code);
}
