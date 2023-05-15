package kz.iitu.tynda.controllers;

import kz.iitu.tynda.models.Song;
import kz.iitu.tynda.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/songs/")
public class SongController {

    private static final String UPLOAD_FOLDER = "/songs/";

    @Autowired
    private FileStorageService service;

    @PostMapping("/upload")
    public ResponseEntity fileUpload(
            @RequestPart("dto") Song song,
            @RequestPart("file") MultipartFile[] files
    ) {
        String msg = "You successfully uploaded: " + System.lineSeparator();
        long totalSize = 0;
        try {
            if (files.length == 1) {
                if (!files[0].isEmpty()) {
                    String ext = files[0].getOriginalFilename().substring(files[0].getOriginalFilename().lastIndexOf(".") + 1);
                    if (!ext.equals("mp3")) {
                        msg = "Only .mp3 audio files are allowed!";
                        return ResponseEntity.internalServerError().body(msg);
                    } else {
                        totalSize += files[0].getSize();
                        if (totalSize > 104857600) { // If total files size is bigger than 100 MB
                            msg = "Maximum total files size is 100 MB!";
                            return ResponseEntity.internalServerError().body(msg);
                        }
                    }
                } else {
                    msg = "Please select files to upload!";
                    return ResponseEntity.internalServerError().body(msg);
                }
            }
            for (MultipartFile file : files) {
                totalSize += file.getSize();
            }
            if (totalSize > 104857600) { // If total files size is bigger than 100 MB
                msg = "Maximum total files size is 100 MB!";
                return ResponseEntity.internalServerError().body(msg);
            }
            for (MultipartFile file : files) {
                String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                if (ext.equals("mp3")) {
                    Song newSong = new Song();
                    service.store(newSong);
                    String path = "/songs/" + newSong.getId() + "." + ext;
                    newSong.setName(file.getOriginalFilename().replaceFirst("[.][^.]+$", ""));
                    newSong.setPath(path);
                    newSong.setSize(file.getSize());
                    newSong.setDate(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
                    service.store(newSong);
                    byte[] bytes = file.getBytes();
                    Path location = Paths.get(path);
                    Files.write(location, bytes);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(msg);
    }

    @GetMapping(value = "/{id}.mp3", produces = "audio/mp3")
    public Mono<Resource> getFileById(@PathVariable String id) {
        return service.getMusicStreaming(id);
    }

    @GetMapping("all")
    public ResponseEntity getListFiles() {
        List<Song> files = service.getAllFiles().map(dbFile -> {
//            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/songs/").path(dbFile.getId()).toUriString();

            return new Song(dbFile.getId(), dbFile.getName(), dbFile.getSize(), dbFile.getDate(), dbFile.getPath());
        }).collect(Collectors.toList());

        return ResponseEntity.ok().body(files);
    }

}
