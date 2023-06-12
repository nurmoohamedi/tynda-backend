package kz.iitu.tynda.services;

import kz.iitu.tynda.helpers.exception.AlreadyExistException;
import kz.iitu.tynda.helpers.exception.NotFoundException;
import kz.iitu.tynda.models.*;
import kz.iitu.tynda.repository.ArtistRepository;
import kz.iitu.tynda.repository.MusicRepository;
import kz.iitu.tynda.repository.PlaylistRepository;
import kz.iitu.tynda.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsService {

    PlaylistRepository playlistRepository;
    MusicRepository musicRepository;
    ArtistRepository artistRepository;
    UserRepository userRepository;

    public UserDetailsService(
            PlaylistRepository playlistRepository,
            MusicRepository musicRepository,
            ArtistRepository artistRepository,
            UserRepository userRepository
    ) {
        this.playlistRepository = playlistRepository;
        this.musicRepository = musicRepository;
        this.artistRepository = artistRepository;
        this.userRepository = userRepository;
    }

    public void createUserNewPlaylist(Playlists playlists, Long id) {
        Playlists savedPlaylist = playlistRepository.save(playlists);

        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            user.get().getPlaylists().add(savedPlaylist);
            userRepository.save(user.get());
        }
    }

    public void savePlaylistToUser(int id, String apiType, Long userId) {
        Optional<Playlists> savedPlaylist = playlistRepository.findById(id);
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            Playlists exist = null;
            for(Playlists pl: user.get().getPlaylists()) {
                if (pl.getId() == id) {
                    exist = pl;
                    break;
                }
            }

            if (exist != null) {
                user.get().getPlaylists().remove(exist);
            } else {
                user.get().getPlaylists().add(savedPlaylist.get());
            }
            userRepository.save(user.get());
        }
    }

    public void saveArtistToUser(String artistId, String apiType, Long userId) {
        Optional<Artist> artist = artistRepository.findById(artistId);
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            Artist exist = null;
            for(Artist art: user.get().getArtists()) {
                if (art.getId() == artistId) {
                    exist = art;
                    break;
                }
            }
            if (exist != null) {
                user.get().getArtists().remove(exist);
            } else {
                user.get().getArtists().add(artist.get());
            }
            userRepository.save(user.get());
        }
    }

    public void saveTrackToUser(String musicId, String apiType, Long userId) {
        Optional<Music> artist = musicRepository.findById(musicId);
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
//            user.get().getPlaylists().add(savedPlaylist.get());
            userRepository.save(user.get());
        }
    }

    public boolean isPlaylistAdded(String id, Long userId) {
        Optional<User> user = userRepository.findById(userId);

        boolean exist = false;
        if (user.isPresent()) {
            for(Playlists playlist: user.get().getPlaylists()) {
                if (playlist.getId() == Integer.parseInt(id)) {
                    exist = true;
                    break;
                }
            }
        }
        return exist;
    }

    public boolean isArtistAdded(String id, Long userId) {
        Optional<User> user = userRepository.findById(userId);

        boolean exist = false;
        if (user.isPresent()) {
            for(Artist artist: user.get().getArtists()) {
                if (artist.getId().contains(id)) {
                    exist = true;
                    break;
                }
            }
        }
        return exist;
    }
}