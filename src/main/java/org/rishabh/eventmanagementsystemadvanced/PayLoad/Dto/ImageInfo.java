package org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto;

import java.time.LocalDateTime;

public record ImageInfo(
        String publicId,
        String securedUrl,
        String optimizedUrl,
        String resizedUrl,
        String format,
        LocalDateTime uploadedAt
) {
}
