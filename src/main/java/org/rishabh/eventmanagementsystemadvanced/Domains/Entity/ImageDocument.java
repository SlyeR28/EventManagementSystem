package org.rishabh.eventmanagementsystemadvanced.Domains.Entity;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "Images")
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
