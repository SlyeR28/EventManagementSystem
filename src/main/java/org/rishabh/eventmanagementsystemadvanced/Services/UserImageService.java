package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ImageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserImageService {

    ImageInfo uploadUserImage(MultipartFile file , Long userId) throws IOException;
    String getUserImageUrl(Long userId) throws IOException;
    void deleteUserImage(Long userId) throws IOException;
}
