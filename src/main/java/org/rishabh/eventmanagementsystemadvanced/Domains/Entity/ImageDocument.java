package org.rishabh.eventmanagementsystemadvanced.Domains.Entity;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageDocument {

    @Field(type = FieldType.Keyword)
    private String securedUrl; // main URL to display

    @Field(type = FieldType.Keyword)
    private String publicId;

    @Field(type = FieldType.Keyword)
    private String folder;

    @Field(type = FieldType.Keyword)
    private String format;
}
