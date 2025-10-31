package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import com.cloudinary.Cloudinary;
import jakarta.transaction.Transactional;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Images;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.User;
import org.rishabh.eventmanagementsystemadvanced.Exception.ImageException;
import org.rishabh.eventmanagementsystemadvanced.Exception.UserNotFoundException;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ImageInfo;
import org.rishabh.eventmanagementsystemadvanced.Repository.ImageRepository;
import org.rishabh.eventmanagementsystemadvanced.Repository.UserRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.ImageBase;
import org.rishabh.eventmanagementsystemadvanced.Services.UserImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Transactional
@Service
public class UserImageServiceImpl extends ImageBase implements UserImageService {

     private final UserRepository  userRepository;
     private final ImageRepository imageRepository;
     private final ImageUrlGenerator imageUrlGenerator;

    public UserImageServiceImpl(Cloudinary cloudinary, ImageUrlGenerator imageUrlGenerator,
                                ImageRepository imageRepository, UserRepository userRepository) {
        super(cloudinary);
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.imageUrlGenerator = imageUrlGenerator;
    }

    @Override
    public ImageInfo uploadUserImage(MultipartFile file, Long userId) throws IOException {

        // finding the current user in which we want to upload iamge

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Not Found " + userId));

        // Delete old image if exist
        if(user.getProfileImage() != null){
            delete(user.getProfileImage().getPublicId());
            imageRepository.delete(user.getProfileImage());
        }

        //upload new image
        ImageInfo uploadImage = upload(file , "users/"+userId);

        //Save image metaData to DB
        Images images = Images.builder()
                .publicId(uploadImage.publicId())
                .securedUrl(uploadImage.securedUrl())
                .format(uploadImage.format())
                .folder("users/"+userId)
                .user(user)
                .build();

        Images savedImage = imageRepository.save(images);

        //link user with image

        user.setProfileImage(savedImage);
        userRepository.save(user);

        return uploadImage;
    }

    @Override
    public String getUserImageUrl(Long userId) throws IOException {
        // finding the current user in which we want to upload iamge

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Not Found " + userId));

        if (user.getProfileImage() == null)
            throw new ImageException("User has no profile image");

        return imageUrlGenerator.generateImageUrl(user.getProfileImage().getPublicId());
    }

    @Override
    public void deleteUserImage(Long userId) throws IOException {
        // finding the current user in which we want to upload iamge

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Not Found " + userId));

        if(user.getProfileImage() != null){
            delete(user.getProfileImage().getPublicId());
            imageRepository.delete(user.getProfileImage());
            user.setProfileImage(null);
            userRepository.save(user);
        }else {
            throw new UserNotFoundException("User Not Found " + userId);
        }

    }


}
