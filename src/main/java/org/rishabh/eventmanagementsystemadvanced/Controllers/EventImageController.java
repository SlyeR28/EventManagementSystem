package org.rishabh.eventmanagementsystemadvanced.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ApiResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ImageInfo;
import org.rishabh.eventmanagementsystemadvanced.Services.EventImageService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/event-image")
@RequiredArgsConstructor
public class EventImageController {

    private final EventImageService eventImageService;



    @PostMapping("/upload/{eventId}")
    public ResponseEntity<List<ImageInfo>>  uploadOrUpdateEventImages(@Valid @PathVariable("eventId") Long eventId
                                        ,@RequestParam ("files") List<MultipartFile> files) {
        List<ImageInfo> imageInfos = eventImageService.uploadEventImage(files, eventId);
        return ResponseEntity.status(HttpStatus.CREATED).body(imageInfos);
    }

    @GetMapping("/get/{eventId}")
    public ResponseEntity<List<ImageInfo>> getEventImages(@PathVariable("eventId") Long eventId) {
        List<ImageInfo> evetImages = eventImageService.getEvetImages(eventId);
        return ResponseEntity.status(HttpStatus.OK).body(evetImages);
    }


    @DeleteMapping("/del/{eventId}")
    public ResponseEntity<ApiResponse>deleteAllEventImage(@PathVariable("eventId") Long eventId) {
       eventImageService.deleteEventImages(eventId);
       return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Event Image Deleted Successfully"));
    }


    @DeleteMapping("/delete/{eventId}/{publicId}")
    public ResponseEntity<ApiResponse>deleteEventImage(@PathVariable("eventId") Long eventId ,@PathVariable String publicId) {
        eventImageService.deleteEventImage(eventId,publicId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Event Image Deleted Successfully"));
    }



    @GetMapping("/get-by/{eventId}")
    public ResponseEntity<Page<ImageInfo>> getEventImages(
            @PathVariable Long eventId,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size,
            @RequestParam(defaultValue = "uploadedAt", required = false) String sortBy,
            @RequestParam(defaultValue = "desc", required = false) String sortDir
    ) {
        Page<ImageInfo> images = eventImageService.getEventImages(eventId, page, size, sortBy, sortDir);
        return ResponseEntity.ok(images);
    }


}
