package org.rishabh.eventmanagementsystemadvanced.Controllers;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ImageInfo;
import org.rishabh.eventmanagementsystemadvanced.Services.UserImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/user-image")
@RequiredArgsConstructor
public class UserImageController {

   private final UserImageService userImageService;



    @PostMapping("/{userId}")
   public ResponseEntity<ImageInfo> uploadImage(@PathVariable Long userId
            , @RequestParam("file") MultipartFile file) throws IOException {

        ImageInfo imageInfo = userImageService.uploadUserImage(file, userId);

        return ResponseEntity.ok().body(imageInfo);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<String> getUserImage(@PathVariable Long userId) throws IOException {

        return ResponseEntity.ok(userImageService.getUserImageUrl(userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteImage(@PathVariable Long userId) throws IOException {
        userImageService.deleteUserImage(userId);
        return ResponseEntity.ok("Image has been deleted successfully");
    }



}
