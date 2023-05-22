package kz.iitu.tynda.controllers;

import kz.iitu.tynda.helpers.response.ResponseHandler;
import kz.iitu.tynda.models.User;
import kz.iitu.tynda.security.services.UserDetailsImpl;
import kz.iitu.tynda.services.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/collection")
public class UserDetailsController {

    @Autowired
    PlaylistService playlistService;


    @GetMapping("/playlists")
    public ResponseEntity getUsersAllPlaylists(@AuthenticationPrincipal UserDetailsImpl user, Principal principal) {
        System.out.println(user.getUsername());
        String userName = principal.getName();
        try {
            return ResponseHandler.generateResponse("", HttpStatus.OK, 0, playlistService.getPlaylistByUserId(user.getId()));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, 0, null);
        }
    }

    @GetMapping("/artists")
    public ResponseEntity getUsersAllArtists(@AuthenticationPrincipal UserDetailsImpl user, Principal principal) {
        String userName = principal.getName();
        try {
            return ResponseHandler.generateResponse("", HttpStatus.OK, 0, playlistService.getArtistsByUserId(user.getId()));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, 0, null);
        }
    }
}