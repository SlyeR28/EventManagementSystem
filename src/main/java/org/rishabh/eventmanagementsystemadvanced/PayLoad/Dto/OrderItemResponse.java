package org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResponse {

    private Long orderItemId;
    private String eventName;
    private String ticketTypeName;
    private int quantity;
    private double price;

}
