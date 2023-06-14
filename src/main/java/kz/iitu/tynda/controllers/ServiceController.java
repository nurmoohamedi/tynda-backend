package kz.iitu.tynda.controllers;

import kz.iitu.tynda.helpers.constant.AppConstants;
import kz.iitu.tynda.helpers.response.ResponseHandler;
import kz.iitu.tynda.models.Audiobook;
import kz.iitu.tynda.models.Music;
import kz.iitu.tynda.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/")
public class ServiceController {

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("image/{id}")
    public Resource getImage(@PathVariable String id) throws IOException {
        return fileStorageService.downloadImage(id);
    }

    @PostMapping("image/upload")
    public ResponseEntity uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            return ResponseEntity.ok(fileStorageService.uploadImage(file));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("track/{id}.mp3")
    public Mono<Resource> getTrack(@PathVariable String id) throws IOException {
        return fileStorageService.getMusicStreaming(id);
    }

    @PostMapping("track/upload")
    public ResponseEntity uploadTrack(@RequestPart("file") MultipartFile file) throws IOException {
        try {
            return ResponseEntity.ok(fileStorageService.uploadMusic(file));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("track/{id}")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity getMusicById(@PathVariable("id") String id) {
        try {
            return ResponseHandler.generateResponse("", HttpStatus.OK, 0, fileStorageService.getMusic(id));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, 1, null);
        }
    }

    @GetMapping("track/all")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity getAllMusic(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        System.out.println("start");
        return ResponseHandler.generateResponse("", HttpStatus.OK, 0, fileStorageService.getAllFiles());
    }
}
