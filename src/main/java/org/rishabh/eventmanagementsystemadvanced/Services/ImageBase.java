package org.rishabh.eventmanagementsystemadvanced.Services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Exception.ImageException;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ImageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@RequiredArgsConstructor
public abstract class ImageBase {


    private final Cloudinary cloudinary;

    protected ImageInfo upload(MultipartFile file , String folder){
        try{
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", folder));
            return new ImageInfo(
                    (String)uploadResult.get("public_id"),
                    (String)uploadResult.get("secured_url"),
                    (String)uploadResult.get("format"),
                    LocalDateTime.now()
            );
        }catch (IOException e){
            throw new ImageException("Error uploading file");
        }
    }

    protected void delete(String public_id){
        try{
            cloudinary.uploader().destroy(public_id , ObjectUtils.emptyMap());
        }catch ( IOException e){
            throw new ImageException("Error deleting file");
        }
    }

}
