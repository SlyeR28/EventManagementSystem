package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.User;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ImageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserImageService {

    ImageInfo uploadUserImage(MultipartFile file , User user) throws IOException;
    ImageInfo updateUserImage(MultipartFile file , User user) throws IOException;
    ImageInfo getUserImage(User user);
    void deleteUserImage(User user);
}
