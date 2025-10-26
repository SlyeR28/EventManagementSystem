package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import com.cloudinary.Cloudinary;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Event;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Images;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ImageInfo;
import org.rishabh.eventmanagementsystemadvanced.Repository.ImageRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.EventImageService;
import org.rishabh.eventmanagementsystemadvanced.Services.ImageBase;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventImageServiceImpl extends ImageBase implements EventImageService {

    private final ImageRepository imageRepository;


    public EventImageServiceImpl(Cloudinary cloudinary, ImageRepository imageRepository) {
        super(cloudinary);
        this.imageRepository = imageRepository;
    }

    @Override
    public List<ImageInfo> uploadEventImage(List<MultipartFile> files, Event event) {
        List<ImageInfo> imageInfos = new ArrayList<>();

        for(MultipartFile file : files){
            ImageInfo imageInfo = upload(file , "eventImages");
            Images image = Images.builder()
                     .publicId(imageInfo.publicId())
                    .securedUrl(imageInfo.securedUrl())
                    .format(imageInfo.format())
                    .folder("events")
                    .event(event)
                    .uploadedAt(imageInfo.uploadedAt())
                    .build();
            imageRepository.save(image);
            imageInfos.add(imageInfo);
        }
       return imageInfos;
    }

    @Override
    public List<ImageInfo> updateEventImage(List<MultipartFile> files, Event event) {
       List<Images> imageInfos = new ArrayList<>();
        for(Images old : imageInfos){
            delete(old.getPublicId());
            imageRepository.delete(old);
        }
        return uploadEventImage(files, event);
    }

    @Override
    public List<ImageInfo> getEvetImages(Event event) {
     return null;
    }

    @Override
    public void deleteEventImages(Event event) {
        List<Images> imageInfos = new ArrayList<>();
        for(Images old : imageInfos){
            delete(old.getPublicId());
            imageRepository.delete(old);
        }
    }
}
