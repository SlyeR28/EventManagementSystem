package org.rishabh.eventmanagementsystemadvanced.PayLoad.Request;

import lombok.Data;

@Data
public class SearchRequest {

    private String keyword;       // for name/venue/category
    private String category;
    private String organizer;
    private String venue;
}
