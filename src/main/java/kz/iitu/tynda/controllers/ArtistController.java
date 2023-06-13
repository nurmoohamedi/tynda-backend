package kz.iitu.tynda.controllers;

import kz.iitu.tynda.helpers.exception.NotFoundException;
import kz.iitu.tynda.helpers.response.ResponseHandler;
import kz.iitu.tynda.models.Artist;
import kz.iitu.tynda.models.Music;
import kz.iitu.tynda.models.MusicDTO;
import kz.iitu.tynda.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/artist")
public class ArtistController {

    @Autowired
    private ArtistRepository artistRepository;

    @GetMapping("/{id}")
    public ResponseEntity getArtistById(@PathVariable String id) {
        try {
            Optional<Artist> artist = artistRepository.findById(id);
//            ArtistDTO artistDTO = null;
            List<MusicDTO> musicDTOList = new ArrayList<>();
            if (artist.isPresent()) {
                if (artist.get().getMusics().size() > 0) {
                    for (Music music: artist.get().getMusics()) {
                        MusicDTO musicDTO = new MusicDTO(music);
                        musicDTOList.add(musicDTO);
                    }
                }
            } else {
                throw new NotFoundException("Artist not found!");
            }

            artist.get().setMusicDTOList(musicDTOList);
            artist.get().setMusics(null);

            return ResponseHandler.generateResponse("", HttpStatus.OK, 0, artist.get());
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Smth wrong!", HttpStatus.INTERNAL_SERVER_ERROR, 1, null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity getAllArtists() {
        try {
            return ResponseHandler.generateResponse("", HttpStatus.OK, 0, artistRepository.findAll());
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Smth wrong!", HttpStatus.INTERNAL_SERVER_ERROR, 1, null);
        }
    }

    @PostMapping
    public ResponseEntity addArtist(@RequestBody Artist artist) {
        try {
            return ResponseHandler.generateResponse("Artist added succesfully!", HttpStatus.OK, 0, artistRepository.save(artist));
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Smth wrong!", HttpStatus.INTERNAL_SERVER_ERROR, 1, null);
        }
    }
}
