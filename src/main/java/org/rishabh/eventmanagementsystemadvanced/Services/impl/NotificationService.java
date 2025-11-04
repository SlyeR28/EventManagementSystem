package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.NotificationLog;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.NotificationTemplate;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.UserPrefernces;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.ChannelType;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.NotficationRequest;
import org.rishabh.eventmanagementsystemadvanced.Repository.NotificationLogRepository;
import org.rishabh.eventmanagementsystemadvanced.Repository.UserPreferncesRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.NotificationChannel;
import org.rishabh.eventmanagementsystemadvanced.Services.TemplateService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final TemplateService templateService;
    private final NotificationChannelResolver notificationChannelResolver;
    private final NotificationLogRepository notificationLogRepository;
    private final UserPreferncesRepository userPreferncesRepository;

    @Async("notifExecutor")
    public void sendNotification(NotficationRequest req) {
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

        // ✅ Fetch user preferences
        Optional<UserPrefernces> prefOpt = userPreferncesRepository.findByUserId(req.getUserId());
        boolean sendEmail = true;
        boolean sendPopup = true;

        if (prefOpt.isPresent()) {
            sendEmail = prefOpt.get().isEmailEnabled();
            sendPopup = prefOpt.get().isPopupEnabled();
        }

        // ✅ Send only via enabled channels
        if (template.getChannel() == ChannelType.EMAIL && sendEmail) {
            sendToChannel(template.getChannel(), req);
        } else if (template.getChannel() == ChannelType.POPUP && sendPopup) {
            sendToChannel(template.getChannel(), req);
        } else {
            // Skip if disabled
            saveNotificationLog(req, template.getChannel(), "SKIPPED");
            return;
        }

        // ✅ Save log as SENT
        saveNotificationLog(req, template.getChannel(), "SENT");
    }

    private void sendToChannel(ChannelType type, NotficationRequest req) {
        NotificationChannel channel = notificationChannelResolver.resolve(type);
        channel.send(req);
    }

    private void saveNotificationLog(NotficationRequest req, ChannelType type, String status) {
        notificationLogRepository.save(NotificationLog.builder()
                .userId(req.getUserId())
                .email(req.getUserEmail())
                .channel(type)
                .templateCode(req.getTemplateCode())
                .subject(req.getSubject())
                .message(req.getMessage())
                .status(status)
                .sentAt(LocalDateTime.now())
                .build());
    }
}
