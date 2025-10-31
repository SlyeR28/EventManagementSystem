package org.rishabh.eventmanagementsystemadvanced.PayLoad.Request;

import lombok.Data;

@Data
public class TicketRequest {

    private Long ticketTypeId;
    private String ticketTypeName;
    private Long purchaserId;
}
