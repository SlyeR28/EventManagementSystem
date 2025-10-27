package org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto;

import java.time.LocalDateTime;

public record ImageInfo(
        String publicId,
        String securedUrl,
        String format,
        LocalDateTime uploadedAt
) {
}
