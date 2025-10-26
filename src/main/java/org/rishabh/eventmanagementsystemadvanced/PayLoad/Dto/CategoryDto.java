package org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDto {

    private Long id;
    private String name;
    private Set<Long> eventIds;

}
