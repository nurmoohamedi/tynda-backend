package kz.iitu.tynda.services;

import kz.iitu.tynda.models.Song;
import kz.iitu.tynda.repository.FileStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import reactor.core.publisher.Mono;
import org.springframework.core.io.Resource;

@Service
public class FileStorageService {

    @Value("${server.base_path}")
    String basePath;

    @Autowired
    private FileStorageRepository fileStorageRepository;

    public Song store(Song song) throws IOException {
//        Song song = new Song(song.getName(), song.getSize(), song.getDate(), song.getPath());
        return fileStorageRepository.save(song);
    }

    public Optional<Song> getSong(String id) {
        return fileStorageRepository.findById(id);
    }

    public Stream<Song> getAllFiles() {
        return fileStorageRepository.findAll().stream();
    }

    public Mono<Resource> getMusicStreaming(String id) {
        Song song = this.getSong(id).get();
        // String path = "classpath:" + basePath + "/" + file.getName() + ".mp3";
        String path = Paths.get(basePath, song.getName() + ".mp3").toString();
        FileSystemResource fileSystemResource = new FileSystemResource(path);
        return Mono.fromSupplier(() -> fileSystemResource);
    }
}
