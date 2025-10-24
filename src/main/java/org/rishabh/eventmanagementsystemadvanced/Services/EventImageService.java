package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Event;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ImageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EventImageService {

    List<ImageInfo> uploadEventImage(List<MultipartFile> files , Event event);
    List<ImageInfo> updateEventImage(List<MultipartFile> files , Event event);
    List<ImageInfo>getEvetImages(Event event);
    void deleteEventImages(Event event);
}
