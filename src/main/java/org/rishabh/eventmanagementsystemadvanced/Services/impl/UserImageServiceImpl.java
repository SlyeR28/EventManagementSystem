package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import com.cloudinary.Cloudinary;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Images;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.User;
import org.rishabh.eventmanagementsystemadvanced.Mapper.ImageMapper;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ImageInfo;
import org.rishabh.eventmanagementsystemadvanced.Repository.ImageRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.ImageBase;
import org.rishabh.eventmanagementsystemadvanced.Services.UserImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UserImageServiceImpl extends ImageBase implements UserImageService {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    public UserImageServiceImpl(Cloudinary cloudinary , ImageRepository imageRepository , ImageMapper imageMapper) {
        super(cloudinary);
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
    }

    @Override
    public ImageInfo uploadUserImage(MultipartFile file, User user) throws IOException {
        imageRepository.findFirstByUser(user).ifPresent(old -> {
            delete(old.getPublicId());
            imageRepository.delete(old);
        });

        ImageInfo imageInfo = upload(file , "userImages");
        Images image = Images.builder()
                .publicId(imageInfo.publicId())
                .securedUrl(imageInfo.securedUrl())
                .format(imageInfo.format())
                .folder("users")
                .user(user)
                .uploadedAt(imageInfo.uploadedAt())
                .build();

        return null;
    }

    @Override
    public ImageInfo updateUserImage(MultipartFile file, User user) throws IOException {
        return null;
    }

    @Override
    public ImageInfo getUserImage(User user) {
        return null;
    }

    @Override
    public void deleteUserImage(User user) {

    }
}
