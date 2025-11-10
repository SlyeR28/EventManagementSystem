package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.NotificationTemplate;
import org.rishabh.eventmanagementsystemadvanced.Repository.NotificationTemplateRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.TemplateService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class SimpleTemplateService  implements TemplateService {

    private final NotificationTemplateRepository templateRepository;

    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{\\{(\\w+)\\}\\}");


    @Override
    public List<NotificationTemplate> findByCode(String code) {
        return templateRepository.findByCode(code);
    }

    @Override
    public String render(String template, Map<String, Object> vars) {
        if (template == null) return "";
        if (vars == null || vars.isEmpty()) return template;

        Matcher matcher = PLACEHOLDER_PATTERN.matcher(template);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String key = matcher.group(1); // The content inside {{...}}
            Object value = vars.getOrDefault(key, "{" + key + "}"); // Fallback to placeholder if key is missing
            matcher.appendReplacement(result, Matcher.quoteReplacement(String.valueOf(value)));
        }
        matcher.appendTail(result);

        return result.toString();
    }
}
