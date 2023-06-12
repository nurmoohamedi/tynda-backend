package kz.iitu.tynda.controllers;

import kz.iitu.tynda.helpers.constant.AppConstants;
import kz.iitu.tynda.helpers.response.ResponseHandler;
import kz.iitu.tynda.models.Audiobook;
import kz.iitu.tynda.models.Playlists;
import kz.iitu.tynda.repository.AudiobookRepository;
import kz.iitu.tynda.services.FileStorageService;
import kz.iitu.tynda.services.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/")
public class AudiobookController {

  @Autowired
  AudiobookRepository audiobookRepository;

  @Autowired
  FileStorageService fileStorageService;

  @GetMapping("audiobook/all")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity getAll(
    @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
    @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
    @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
    @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
  ) {
    return ResponseHandler.generateResponse("", HttpStatus.OK, 0, audiobookRepository.findAll());
  }

  @GetMapping("audiobook/{id}")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity getAudiobookById(@PathVariable("id") String id) {
    try {
      return ResponseHandler.generateResponse("", HttpStatus.OK, 0, audiobookRepository.findById(id).get());
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, 1, null);
    }
  }

  @PostMapping("audiobook/save")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity saveAudiobook(
          @RequestPart(value = "dto") Audiobook audiobook,
          @RequestPart(value = "file", required = false) MultipartFile file
          ) {
    try {
      return ResponseHandler.generateResponse("Saved success", HttpStatus.OK, 0, fileStorageService.uploadAudiobookMusic(audiobook, file));
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, 1, null);
    }
  }

  //  TODO
  @DeleteMapping("audiobook/delete/{id}")
  public ResponseEntity deleteAudiobook(@PathVariable("id") String id) {
    try {
      audiobookRepository.deleteById(id);
      return ResponseHandler.generateResponse("Deleted success", HttpStatus.OK, 0, null);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, 1, null);
    }
  }
}