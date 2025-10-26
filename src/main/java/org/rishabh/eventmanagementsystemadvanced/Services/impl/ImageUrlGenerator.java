package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.AppConstants.ImageConstants;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ImageUrlGenerator {

    private final Cloudinary cloudinary;

    public String generateImageUrl(String publicId) throws IOException {
        return cloudinary.url().generate(publicId);
    }

    public String generateResizedUrl(String publicId) throws IOException {
        return cloudinary.url()
                .secure(true)
                .transformation(
                        new Transformation()
                                .height(ImageConstants.CLOUDNARY_IMAGE_HEIGHT)
                                .width(ImageConstants.CLOUDNARY_IMAGE_WIDTH)
                                .crop(ImageConstants.CLOUDNARY_IMAGE_CROP)
                ).generate(publicId);
    }

    public String optimizedUrl(String publicId) throws IOException {
        return cloudinary.url()
                .secure(true)
                .transformation(new Transformation()
                        .quality("auto")
                        .fetchFormat("auto"))
                .generate(publicId);
    }


}
