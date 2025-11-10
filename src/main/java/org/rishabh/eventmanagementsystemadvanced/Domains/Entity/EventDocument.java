package org.rishabh.eventmanagementsystemadvanced.Domains.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "events")
public class EventDocument {

    @Id
    private Long id; // eventId from MySQL

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Keyword)
    private String venue;

    @Field(type = FieldType.Keyword)
    private String status; // EventStatus: DRAFT, PUBLISHED, ONGOING

    @Field(type = FieldType.Date)
    private LocalDate startTime;

    @Field(type = FieldType.Date)
    private LocalDate endTime;

    @Field(type = FieldType.Keyword)
    private String categoryName; // store category name

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<TicketTypeDocument> ticketTypes;

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<ImageDocument> images;
}
