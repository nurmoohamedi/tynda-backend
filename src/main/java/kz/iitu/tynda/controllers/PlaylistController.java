package kz.iitu.tynda.controllers;

import kz.iitu.tynda.helpers.response.ResponseHandler;
import kz.iitu.tynda.models.Playlists;
import kz.iitu.tynda.helpers.constant.AppConstants;
import kz.iitu.tynda.services.PlaylistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/")
public class PlaylistController {

  static String LIKED_SONG = "https://t.scdn.co/images/3099b3803ad9496896c43f22fe9be8c4.png";

  PlaylistService playlistService;

  public PlaylistController(PlaylistService playlistService) {
    this.playlistService = playlistService;
  }

  @GetMapping("playlist/all")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity getAll(
    @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
    @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
    @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
    @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
  ) {

    System.out.println("start");
    return ResponseHandler.generateResponse("", HttpStatus.OK, 0, playlistService.getAll(pageNo, pageSize, sortBy, sortDir));
  }

  @GetMapping("playlist/{id}")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity getPlaylistById(@PathVariable("id") int id) {
    try {
      return ResponseHandler.generateResponse("", HttpStatus.OK, 0, playlistService.getPlaylistById(id));
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, 1, null);
    }
  }

  @PostMapping("playlist/save")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity savePlaylist(@RequestBody Playlists playlists) {
    System.out.println("save-1");
    System.out.println(playlists.toString());
    try {
      playlistService.savePlaylist(playlists);
      return ResponseHandler.generateResponse("Saved success", HttpStatus.OK, 0, null);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, 1, null);
    }
  }

  @PutMapping("playlist/update/{id}")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity updatePlaylist(
    @PathVariable("id") int id,
    @RequestBody Playlists playlists
  ) {
    System.out.println("update-1");
    try {
      playlistService.updatePlaylist(id, playlists);
      return ResponseHandler.generateResponse("Saved success", HttpStatus.OK, 0, null);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, 1, null);
    }
  }

  @DeleteMapping("playlist/delete/{id}")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity deletePlaylist(@PathVariable("id") int id) {
    System.out.println("delete-1");
    try {
      playlistService.deletePlaylist(id);
      return ResponseHandler.generateResponse("Deleted success", HttpStatus.OK, 0, null);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, 1, null);
    }
  }

  @GetMapping("music/{id}")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity getMusicById(@PathVariable("id") int id) {
    try {
      return ResponseHandler.generateResponse("", HttpStatus.OK, 0, playlistService.getMusicById(id));
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, 1, null);
    }
  }

  @GetMapping("music/all")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity getAllMusic(
    @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
    @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
    @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
    @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
  ) {
    System.out.println("start");
    return ResponseHandler.generateResponse("", HttpStatus.OK, 0, playlistService.getAllMusic(pageNo, pageSize, sortBy, sortDir));
  }


  @PostMapping(value = "playlist/{id}/save-music")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity saveMusicToPlaylist(@RequestParam("musicId") int musicId, @PathVariable("id") int id) {
    try {
      playlistService.addMusicToPlaylist(id, musicId);
      return ResponseHandler.generateResponse("Saved success", HttpStatus.OK, 0, null);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, 1, null);
    }
  }
}