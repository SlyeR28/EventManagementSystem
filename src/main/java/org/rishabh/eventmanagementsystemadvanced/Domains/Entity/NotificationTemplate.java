package org.rishabh.eventmanagementsystemadvanced.Domains.Entity;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.ChannelType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notification_templates")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationTemplate {

    @Id
    private String id;
    private String code;
    private String subject;
    private String body;
    private ChannelType channel;
}
