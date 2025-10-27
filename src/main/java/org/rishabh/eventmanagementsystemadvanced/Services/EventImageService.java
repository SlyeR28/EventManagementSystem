package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ImageInfo;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EventImageService {

    List<ImageInfo> uploadEventImage(List<MultipartFile> files , Long eventId);

    List<ImageInfo>getEvetImages(Long eventId);
    void deleteEventImages(Long eventId);
    void deleteEventImage(Long eventId , String publicId);

    @Transactional(readOnly = true)
    Page<ImageInfo> getEventImages(Long eventId, int page, int size, String sortBy, String sortDir);
}
