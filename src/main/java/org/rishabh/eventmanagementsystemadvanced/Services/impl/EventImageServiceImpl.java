package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import com.cloudinary.Cloudinary;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Event;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Images;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ImageInfo;
import org.rishabh.eventmanagementsystemadvanced.Repository.EventRepository;
import org.rishabh.eventmanagementsystemadvanced.Repository.ImageRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.EventImageService;
import org.rishabh.eventmanagementsystemadvanced.Services.ImageBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class EventImageServiceImpl extends ImageBase implements EventImageService {

    private final EventRepository eventRepository;
    private final ImageRepository imageRepository;


    public EventImageServiceImpl(Cloudinary cloudinary, EventRepository eventRepository
            , ImageRepository imageRepository) {
        super(cloudinary);
        this.eventRepository = eventRepository;
        this.imageRepository = imageRepository;

    }


    @Override
    public List<ImageInfo> uploadEventImage(List<MultipartFile> files, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found" + eventId));
        // if event already has images -> delete from cloudinary and DB
        if(event.getImages() != null && !event.getImages().isEmpty()){
               event.getImages().forEach(image-> delete(image.getPublicId()));
               imageRepository.deleteAll(event.getImages());
               event.getImages().clear();
           }
        // Upload new images
        List<Images> uploadedImages = files.stream()
                .map(file -> {
                    ImageInfo info = upload(file, "eventImage" + eventId);
                    return Images.builder()
                            .publicId(info.publicId())
                            .securedUrl(info.securedUrl())
                            .format(info.format())
                            .uploadedAt(info.uploadedAt())
                            .event(event)
                            .build();
                })
                .collect(Collectors.toList());

        imageRepository.saveAll(uploadedImages);
        event.getImages().addAll(uploadedImages);

        eventRepository.save(event);

        // Return ImageInfo list for API response
        return uploadedImages.stream()
                .map(img -> new ImageInfo(
                        img.getPublicId(),
                        img.getSecuredUrl(),
                        img.getFormat(),
                        img.getUploadedAt()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<ImageInfo> getEvetImages(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found" + eventId));
        return event.getImages().stream().map(
                img-> new ImageInfo(
                        img.getPublicId(),
                        img.getSecuredUrl(),
                        img.getFormat(),
                        img.getUploadedAt()
                )).collect(Collectors.toList());

    }

    @Override
    public void deleteEventImages(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found" + eventId));

        event.getImages().forEach(image-> delete(image.getPublicId()));
        imageRepository.deleteAll(event.getImages());
        event.getImages().clear();
        eventRepository.save(event);

    }

    @Override
    public void deleteEventImage(Long eventId, String publicId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found" + eventId));
        Images eventImage = event.getImages().stream()
                .filter(img -> img.getPublicId().equals(publicId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("event image not found" + eventId));
        delete(publicId);
        imageRepository.delete(eventImage);
        event.getImages().remove(eventImage);
        eventRepository.save(event);

    }

    @Transactional(readOnly = true)
    @Override
    public Page<ImageInfo> getEventImages(Long eventId, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Images> images = imageRepository.findByEventId(eventId, pageable);

        return images.map(img -> new ImageInfo(
                img.getPublicId(),
                img.getSecuredUrl(),
                img.getFormat(),
                img.getUploadedAt()
        ));
    }
}
