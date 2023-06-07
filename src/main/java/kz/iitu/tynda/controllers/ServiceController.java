package kz.iitu.tynda.controllers;

import kz.iitu.tynda.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
}
