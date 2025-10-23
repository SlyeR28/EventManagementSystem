package org.rishabh.eventmanagementsystemadvanced.Controllers;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ImageInfo;
import org.rishabh.eventmanagementsystemadvanced.Services.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/image")
@RequiredArgsConstructor
public class ImageController {

//    private final ImageService imageService;
//
//
//    @PostMapping("/upload")
//    public ResponseEntity<ImageInfo> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
//        ImageInfo imageInfo = imageService.uploadImage(file);
//       return ResponseEntity.ok().body(imageInfo);
//    }


}
