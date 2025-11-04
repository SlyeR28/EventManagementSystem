package org.rishabh.eventmanagementsystemadvanced.PayLoad.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotficationRequest
{
    Long userId;
    String userEmail;
    String subject;
     String message;
     String templateCode;
}
