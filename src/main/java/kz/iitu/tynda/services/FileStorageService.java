package kz.iitu.tynda.services;

import kz.iitu.tynda.models.Audiobook;
import kz.iitu.tynda.models.Image;
import kz.iitu.tynda.models.Music;
import kz.iitu.tynda.repository.AudiobookRepository;
import kz.iitu.tynda.repository.ImageRepository;
import kz.iitu.tynda.repository.MusicRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileStorageService {

    String absolutePath = new FileSystemResource("").getFile().getAbsolutePath();

    @Autowired
    ServletContext context;

    private MusicRepository musicRepository;
    private ImageRepository imageRepository;
    private AudiobookRepository audiobookRepository;

    public FileStorageService(
            MusicRepository musicRepository,
            ImageRepository imageRepository,
            AudiobookRepository audiobookRepository
    ) {
        this.musicRepository = musicRepository;
        this.imageRepository = imageRepository;
        this.audiobookRepository = audiobookRepository;
    }

    public Music getMusic(String id) {
        return musicRepository.findById(id).get();
    }

    public List<Music> getAllFiles() {
        return musicRepository.findAll();
    }

    public Mono<Resource> getMusicStreaming(String id) {
        Music music = this.getMusic(id);
        // String path = "classpath:" + musicStoragePath + "/" + file.getName() + ".mp3";
//        String path = Paths.get(absolutePath + "\\Musics\\", music.getId() + ".mp3").toString();
        FileSystemResource fileSystemResource = new FileSystemResource(music.getLocal_path());
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
            String absolutePath = this.absolutePath + "\\images\\";
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

    public Music uploadMusic(MultipartFile file) throws IOException {
        Optional<Music> existedMusic = musicRepository.findByName(file.getOriginalFilename());
        System.out.println(file.getOriginalFilename());

        if (existedMusic.isPresent()) {
            return existedMusic.get();
        } else {
            String randomId = UUID.randomUUID().toString();
            String fileType = file.getContentType().split("/")[1];

            String absolutePath = this.absolutePath + "\\musics\\";
            String localPath = absolutePath + randomId + "." + fileType;
            String publicPath = "http://localhost:8080/api/track/" + randomId + ".mp3";

            Music music = new Music(
                    randomId,
                    file.getOriginalFilename(),
                    file.getSize(),
                    null,
                    localPath,
                    publicPath,
                    null
            );

            file.transferTo(new File(localPath));
            return musicRepository.save(music);
        }
    }

    public Audiobook uploadAudiobookMusic(Audiobook audiobook, MultipartFile file) throws IOException {
        if (audiobook.getMusics() != null) {
            Audiobook savedAudiobook = audiobookRepository.save(audiobook);
            return savedAudiobook;
        } else {
            Optional<Music> existedMusic = musicRepository.findByName(file.getOriginalFilename());
            System.out.println(file.getOriginalFilename());

            if (existedMusic.isPresent()) {
                audiobook.getMusics().add(existedMusic.get());
                Audiobook savedAudiobook = audiobookRepository.save(audiobook);
                return savedAudiobook;
            } else {
                String randomId = UUID.randomUUID().toString();
                String fileType = file.getContentType().split("/")[1];

                String absolutePath = this.absolutePath + "\\musics\\";
                String localPath = absolutePath + randomId + "." + fileType;
                String publicPath = "http://localhost:8080/api/track/" + randomId + ".mp3";

                Music music = new Music(
                        randomId,
                        file.getOriginalFilename(),
                        file.getSize(),
                        audiobook.getImg_link(),
                        localPath,
                        publicPath,
                        null
                );

                file.transferTo(new File(localPath));
                Music savedMusic = musicRepository.save(music);

                audiobook.getMusics().add(savedMusic);
                Audiobook savedAudiobook = audiobookRepository.save(audiobook);
                return savedAudiobook;
            }
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
