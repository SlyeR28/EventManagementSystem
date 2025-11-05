package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.NotificationTemplate;
import org.rishabh.eventmanagementsystemadvanced.Repository.NotificationTemplateRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.TemplateService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SimpleTemplateService  implements TemplateService {

    private final NotificationTemplateRepository templateRepository;


    @Override
    public List<NotificationTemplate> findByCode(String code) {
        return templateRepository.findByCode(code);
    }

    @Override
    public String render(String template, Map<String, Object> vars) {
        if (template == null) return "";
        if (vars == null || vars.isEmpty()) return template;

        String result = template;
        for (var entry : vars.entrySet()) {
            result = result.replace("{{" + entry.getKey() + "}}", String.valueOf(entry.getValue()));
        }
        return result;
    }
}
