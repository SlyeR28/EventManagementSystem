package org.rishabh.eventmanagementsystemadvanced.Controllers;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.User;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ImageInfo;
import org.rishabh.eventmanagementsystemadvanced.Repository.UserRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.UserImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/user-image")
@RequiredArgsConstructor
public class UserImageController {

   private final UserImageService userImageService;
   private final UserRepository userRepository;


    @PostMapping("/{userId}")
   public ResponseEntity<ImageInfo> uploadImage(@PathVariable Long userId
            , @RequestParam("file") MultipartFile file) throws IOException {

       User user = userRepository.findById(userId).orElseThrow
               (() -> new UsernameNotFoundException("User not found"));

        ImageInfo imageInfo = userImageService.uploadUserImage(file, user);

        return ResponseEntity.ok().body(imageInfo);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ImageInfo> updateImage(@PathVariable Long userId
            , @RequestParam("file") MultipartFile file) throws IOException {

        User user = userRepository.findById(userId).orElseThrow
                (() -> new UsernameNotFoundException("User not found"));

        ImageInfo imageInfo = userImageService.updateUserImage(file, user);

        return ResponseEntity.ok().body(imageInfo);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ImageInfo> ugetImage(@PathVariable Long userId
            , @RequestParam("file") MultipartFile file) throws IOException {

        User user = userRepository.findById(userId).orElseThrow
                (() -> new UsernameNotFoundException("User not found"));

        ImageInfo imageInfo = userImageService.getUserImage(user);

        return ResponseEntity.ok().body(imageInfo);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteImage(@PathVariable Long userId
            , @RequestParam("file") MultipartFile file) throws IOException {
        User user = userRepository.findById(userId).orElseThrow
                (() -> new UsernameNotFoundException("User not found"));
        userImageService.deleteUserImage(user);
        return ResponseEntity.ok().build();
    }



}
