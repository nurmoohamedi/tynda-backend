package kz.iitu.tynda.controllers;

import kz.iitu.tynda.helpers.response.ResponseHandler;
import kz.iitu.tynda.models.Artist;
import kz.iitu.tynda.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/artist")
public class ArtistController {

    @Autowired
    private ArtistRepository artistRepository;

    @GetMapping("/{id}")
    public ResponseEntity getArtistById(@PathVariable String id) {
        try {
            return ResponseHandler.generateResponse("", HttpStatus.OK, 0, artistRepository.findById(id));
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
    public ResponseEntity addArtist(Artist artist) {
        try {
            return ResponseHandler.generateResponse("Artist added succesfully!", HttpStatus.OK, 0, artistRepository.save(artist));
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Smth wrong!", HttpStatus.INTERNAL_SERVER_ERROR, 1, null);
        }
    }
}
