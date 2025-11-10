package org.rishabh.eventmanagementsystemadvanced.PayLoad.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationRequest
{
    private Long userId;
    private String userEmail;
    private String subject;
    private String message;
    private String templateCode;
    @Builder.Default
    private Map<String, Object> variables = new HashMap<>();
}
