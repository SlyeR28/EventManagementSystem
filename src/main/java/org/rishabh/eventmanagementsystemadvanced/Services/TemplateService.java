package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.NotificationTemplate;

import java.util.List;
import java.util.Map;

public interface TemplateService {
    List<NotificationTemplate>findByCode(String code);
    String render(String template , Map<String,Object> vars);
}
