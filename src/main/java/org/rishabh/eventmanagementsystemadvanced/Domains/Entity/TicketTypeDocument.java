package org.rishabh.eventmanagementsystemadvanced.Domains.Entity;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketTypeDocument {

    @Field(type = FieldType.Long)
    private Long ticketTypeId;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Double)
    private Double basePrice;

    @Field(type = FieldType.Double)
    private Double currentPrice;

    @Field(type = FieldType.Integer)
    private Integer totalQuantity;

    @Field(type = FieldType.Integer)
    private Integer remainingQuantity;
}
