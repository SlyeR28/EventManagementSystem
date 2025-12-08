package org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventSearchResponse {


    private Long id;
    private String name;
    private String description;
    private String venue;
    private String categoryName;
    private LocalDate startTime;
    private LocalDate endTime;
    private List<TicketInfo> tickets;
    private List<ImageElkInfo> imageInfos;


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public  static class TicketInfo {

        private Long ticketId;
        private String name;
        private Double currentPrice;
        private Integer remainingQuantity;

    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static  class ImageElkInfo {
        private String securedUrl;
        private String publicId;
        private String folder;
        private String format;

    }


}
