package org.rishabh.eventmanagementsystemadvanced.Domains.Entity;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.ChannelType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notification_logs")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationLog {

    @Id
    private String id;
    private Long userId;
    private String email;
    private ChannelType channel;
    private String templateCode;
    private String subject;
    private String status;
    private String message;
    private LocalDateTime sentAt;

}
