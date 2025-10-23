package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ImageInfo;
import org.rishabh.eventmanagementsystemadvanced.Services.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final Cloudinary cloudinary;

//    @Override
//    public ImageInfo uploadImage(MultipartFile file) throws IOException {
//        Map upload = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
//
//        return new ImageInfo(
//                upload.get("public_id").toString(),
//                upload.get("secured_url").toString(),
//                upload.get("format").toString(),
//                (LocalDateTime) upload.get("uploaded_at")
//        );
//    }
}
