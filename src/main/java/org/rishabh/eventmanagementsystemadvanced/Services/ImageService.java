package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ImageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface ImageService {


    ImageInfo uploadImage(MultipartFile file) throws IOException;


}
