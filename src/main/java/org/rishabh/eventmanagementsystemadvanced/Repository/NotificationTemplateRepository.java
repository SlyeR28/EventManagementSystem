package org.rishabh.eventmanagementsystemadvanced.Repository;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate,Long> {
    List<NotificationTemplate> findByCode(String code);
}
