package org.rishabh.eventmanagementsystemadvanced.PayLoad.Request;

import lombok.Data;

@Data
public class SearchRequest {

    private String keyword;
    private String category;
    private String organizer;
    private String venue;
}
