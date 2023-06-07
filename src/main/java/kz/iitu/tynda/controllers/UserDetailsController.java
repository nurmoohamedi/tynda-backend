package kz.iitu.tynda.controllers;

import kz.iitu.tynda.helpers.response.ResponseHandler;
import kz.iitu.tynda.models.Image;
import kz.iitu.tynda.models.User;
import kz.iitu.tynda.models.UserDTO;
import kz.iitu.tynda.payload.response.JwtResponse;
import kz.iitu.tynda.repository.FileStorageRepository;
import kz.iitu.tynda.repository.UserRepository;
import kz.iitu.tynda.security.jwt.JwtUtils;
import kz.iitu.tynda.security.services.UserDetailsImpl;
import kz.iitu.tynda.services.FileStorageService;
import kz.iitu.tynda.services.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserDetailsController {

    @Autowired
    PlaylistService playlistService;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = {   "multipart/form-data" })
    @ResponseBody
    public ResponseEntity updateUserDetails(
            @RequestPart ("user") UserDTO newUser,
            @RequestPart (value = "file", required = false) MultipartFile file,
            @AuthenticationPrincipal UserDetailsImpl currUser
    ) throws IOException {
        try {
            User savedUser = userRepository.findById(currUser.getId()).get();
            savedUser.setUsername(newUser.getUsername());

            if (file != null) {
                Image savedImage = fileStorageService.uploadImage(file);
                savedUser.setImg_link(savedImage.getPublic_path());
            }

            String newToken = jwtUtils.generateTokenFromUsername(newUser.getUsername());

            User generatedUser = userRepository.save(savedUser);

            JwtResponse response = new JwtResponse(newToken,
                    generatedUser.getId(),
                    generatedUser.getUsername(),
                    generatedUser.getEmail(),
                    generatedUser.getImg_link(),
                    generatedUser.isSubscribed(),
                    generatedUser.getRolesAsList());

            return ResponseHandler.generateResponse(
                    "",
                    HttpStatus.OK,
                    0,
                    response
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, 0, null);
        }
    }

    @GetMapping("/collection/playlists")
    public ResponseEntity getUsersAllPlaylists(@AuthenticationPrincipal UserDetailsImpl user, Principal principal) {
        System.out.println(user.getUsername());
        String userName = principal.getName();
        try {
            return ResponseHandler.generateResponse("", HttpStatus.OK, 0, playlistService.getPlaylistByUserId(user.getId()));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, 0, null);
        }
    }

    @GetMapping("/collection/artists")
    public ResponseEntity getUsersAllArtists(@AuthenticationPrincipal UserDetailsImpl user, Principal principal) {
        String userName = principal.getName();
        try {
            return ResponseHandler.generateResponse("", HttpStatus.OK, 0, playlistService.getArtistsByUserId(user.getId()));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, 0, null);
        }
    }
}