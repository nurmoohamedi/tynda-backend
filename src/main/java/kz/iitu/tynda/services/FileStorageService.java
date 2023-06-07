package kz.iitu.tynda.services;

import kz.iitu.tynda.models.Image;
import kz.iitu.tynda.models.Song;
import kz.iitu.tynda.repository.FileStorageRepository;
import kz.iitu.tynda.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import javax.servlet.ServletContext;
import java.io.*;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FileStorageService {

    @Value("${tynda.base_musics_path}")
    String musicStoragePath;

    String imageStoragePath = new FileSystemResource("").getFile().getAbsolutePath();

    @Autowired
    ServletContext context;

    private FileStorageRepository fileStorageRepository;
    private ImageRepository imageRepository;

    public FileStorageService(FileStorageRepository fileStorageRepository, ImageRepository imageRepository) {
        this.fileStorageRepository = fileStorageRepository;
        this.imageRepository = imageRepository;
    }

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
        // String path = "classpath:" + musicStoragePath + "/" + file.getName() + ".mp3";
        String path = Paths.get(musicStoragePath, song.getName() + ".mp3").toString();
        FileSystemResource fileSystemResource = new FileSystemResource(path);
        return Mono.fromSupplier(() -> fileSystemResource);
    }

    // Image Service Business Logic Layer ----------------------------------
    public Resource downloadImage(String fileName) throws IOException {
        Optional<Image> newImage = imageRepository.findById(fileName);
        if (newImage.isPresent()) {
            String fullPath = newImage.get().getImage_path();
            return loadFileResource(fullPath);
//            return Files.readAllBytes(new File(fullPath).toPath());
        }
        return null;
    }

    public Image uploadImage(MultipartFile file) throws IOException {
        Optional<Image> existedImage = imageRepository.findByName(file.getOriginalFilename());

        System.out.println(file.getOriginalFilename());

        if (existedImage.isPresent()) {
            return existedImage.get();
        } else {
            String absolutePath = imageStoragePath + "\\images\\";
            String randomId = UUID.randomUUID().toString();

            String fileType = file.getContentType().split("/")[1];
            String fullPath = absolutePath + randomId + "." + fileType;
            String publicPath = "http://localhost:8080/api/image/" + randomId;
            Image newImage = new Image(
                    randomId,
                    file.getOriginalFilename(),
                    file.getContentType(),
                    fullPath,
                    publicPath
            );

            file.transferTo(new File(fullPath));
            return imageRepository.save(newImage);
        }
    }

    public Resource loadFileResource(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                InputStream in = new FileInputStream(file);
                return new InputStreamResource(in);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
