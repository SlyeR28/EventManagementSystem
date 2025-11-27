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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final TemplateService templateService;
    private final NotificationChannelResolver notificationChannelResolver;
    private final UserPreferncesRepository userPreferncesRepository;

    @Async("notifExecutor")
    public void sendNotification(NotificationRequest req) {

            NotificationTemplate template = templateService.findByCode(req.getTemplateCode())
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Template code not found: " + req.getTemplateCode()));


            String renderedMessage = templateService.render(template.getBody(), req.getVariables());
            req.setMessage(renderedMessage);
            req.setSubject(template.getSubject());


            Optional<UserPrefernces> prefOpt = userPreferncesRepository.findByUserId(req.getUserId());
            boolean emailEnabled = prefOpt.map(UserPrefernces::isEmailEnabled).orElse(true);
            boolean popupEnabled = prefOpt.map(UserPrefernces::isPopupEnabled).orElse(true);


            for (ChannelType channelType : template.getChannels()) {
                boolean userHasEnabledChannel = false;

                if (channelType == ChannelType.EMAIL) {
                    userHasEnabledChannel = emailEnabled;
                } else if (channelType == ChannelType.POPUP) {
                    userHasEnabledChannel = popupEnabled;
                }

                if (userHasEnabledChannel) {
                    sendToChannel(channelType, req);

                }
            }
    }


    private void sendToChannel(ChannelType type, NotificationRequest req) {
        NotificationChannel channel = notificationChannelResolver.resolve(type);
        channel.send(req);
    }


}
