package org.rishabh.eventmanagementsystemadvanced.PayLoad.Request;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record ImageRequest(
        @NotNull(message = "File is required")
        MultipartFile file
) {
}
