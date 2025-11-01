package org.rishabh.eventmanagementsystemadvanced.Domains.Entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.MessageType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Document(collection = "chat_messages")
public class ChatMessages {

    @Id
    private String id;

    private Long senderId;
    private Long receiverId;  // optional for private messages

    private MessageType type;

    private String content;

    private Instant timestamp;

    private boolean read;




}
