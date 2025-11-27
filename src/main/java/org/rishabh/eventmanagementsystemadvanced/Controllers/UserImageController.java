package org.rishabh.eventmanagementsystemadvanced.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ImageInfo;
import org.rishabh.eventmanagementsystemadvanced.Services.UserImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/user-image")
@RequiredArgsConstructor
@Tag(name = "UserImage" , description = "APIs Related to User Image Operations")
public class UserImageController {

   private final UserImageService userImageService;




    @Operation(
            summary = "Upload user profile image",
            description = "Uploading user's images to cloudinary to get rid of storing on Server side"
    )

    @ApiResponse(responseCode = "201" , description = "User Image Uploaded successfully")
    @ApiResponse(responseCode = "400" , description = "Invalid Input format ")
    @ApiResponse(responseCode = "409" , description = "Duplicate Images found")
    @ApiResponse(responseCode = "500" , description = "Internal server error")

    @PostMapping("/{userId}")
   public ResponseEntity<ImageInfo> uploadImage(@PathVariable Long userId
            , @RequestParam("file") MultipartFile file) throws IOException {

        ImageInfo imageInfo = userImageService.uploadUserImage(file, userId);

        return ResponseEntity.ok().body(imageInfo);
    }


    @Operation(
            summary = "Get user profile image",
            description = "Getting user's images From Cloudinary"
    )

    @ApiResponse(responseCode = "200" , description = "User Image Fetched successfully")
    @ApiResponse(responseCode = "400" , description = "Invalid Input format ")
    @ApiResponse(responseCode = "500" , description = "Internal server error")
    @GetMapping("/{userId}")
    public ResponseEntity<String> getUserImage(@PathVariable Long userId) throws IOException {

        return ResponseEntity.ok(userImageService.getUserImageUrl(userId));
    }

    @Operation(
            summary = "Deleting user profile image",
            description = "Deleting user's images From cloudinary"
    )

    @ApiResponse(responseCode = "204" , description = "User Image Deleted  successfully")
    @ApiResponse(responseCode = "404" , description = "Image not found to delete ")
    @ApiResponse(responseCode = "500" , description = "Internal server error")
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteImage(@PathVariable Long userId) throws IOException {
        userImageService.deleteUserImage(userId);
        return ResponseEntity.ok("Image has been deleted successfully");
    }

}
