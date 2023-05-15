package kz.iitu.tynda.controllers;

import kz.iitu.tynda.helpers.response.ResponseHandler;
import kz.iitu.tynda.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/")
public class ServiceController {

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("image/{id}")
    public ResponseEntity getImage(@PathVariable String id) {
        try {
            return ResponseEntity.ok(fileStorageService.downloadImage(id));
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Something Wrong!", HttpStatus.INTERNAL_SERVER_ERROR, 1, null);
        }
    }

    @PostMapping("image/upload")
    public ResponseEntity uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            return ResponseEntity.ok(fileStorageService.uploadImage(file));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
