package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.NotificationTemplate;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.UserPrefernces;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.ChannelType;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.NotificationRequest;
import org.rishabh.eventmanagementsystemadvanced.Repository.UserPreferncesRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.NotificationChannel;
import org.rishabh.eventmanagementsystemadvanced.Services.TemplateService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final TemplateService templateService;
    private final NotificationChannelResolver notificationChannelResolver;
    private final UserPreferncesRepository userPreferncesRepository;

    @Async("notifExecutor")
    public void sendNotification(NotificationRequest req) {
        // Fetch template
        NotificationTemplate template = templateService.findByCode(req.getTemplateCode())
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Template code not found: " + req.getTemplateCode()));

        // Render template message
        String message = templateService.render(template.getBody(), Map.of(
                "userId", req.getUserId(),
                "message", req.getMessage() != null ? req.getMessage() : ""
        ));
        req.setMessage(message);
        req.setSubject(template.getSubject());

        // Fetch user preferences
        Optional<UserPrefernces> prefOpt = userPreferncesRepository.findByUserId(req.getUserId());
        boolean sendEmail = true;
        boolean sendPopup = true;

        if (prefOpt.isPresent()) {
            sendEmail = prefOpt.get().isEmailEnabled();
            sendPopup = prefOpt.get().isPopupEnabled();
        }

        // Send via enabled channels only
        if (template.getChannel() == ChannelType.EMAIL && sendEmail) {
            sendToChannel(template.getChannel(), req);
        } else if (template.getChannel() == ChannelType.POPUP && sendPopup) {
            sendToChannel(template.getChannel(), req);
        }
    }


    private void sendToChannel(ChannelType type, NotificationRequest req) {
        NotificationChannel channel = notificationChannelResolver.resolve(type);
        channel.send(req);
    }


}
