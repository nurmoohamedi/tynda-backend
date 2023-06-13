package kz.iitu.tynda.services;

import kz.iitu.tynda.helpers.exception.NotFoundException;
import kz.iitu.tynda.helpers.response.ResponseHandler;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {

    PlaylistRepository playlistRepository;
    MusicRepository musicRepository;
    ArtistRepository artistRepository;
    UserRepository userRepository;

    public PlaylistService(
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

    public PostResponse getAll(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Playlists> posts  = playlistRepository.findAll(pageable);

        // get content for page object
        List<Playlists> listOfPosts = posts.getContent();

        System.out.println(listOfPosts);

//        List<PostDto> content= listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
//
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(listOfPosts);
        postResponse.setPageNo(posts .getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
//
        return postResponse;
    }

    public Long getPlaylistsSize() {
        return playlistRepository.count();
    }

    public MusicResponse getAllMusic(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);

        Page<Music> posts  = musicRepository.findAll(pageable);

        // get content for page object
        List<Music> listOfPosts = posts.getContent();

        System.out.println(listOfPosts);

//        List<PostDto> content= listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
//
        MusicResponse postResponse = new MusicResponse();
        postResponse.setContent(listOfPosts);
        postResponse.setPageNo(posts .getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
//
        return postResponse;
    }

    public MusicDTO getMusicById(String id) throws NotFoundException {
        Optional<Music> music = musicRepository.findById(id);
//        List<Artist> artists = artistRepository.findArtistsByMusicsId(id);
        if (music.isPresent()) {
            MusicDTO musicDTO = new MusicDTO(music.get());
            return musicDTO;
        }
        else
            throw new NotFoundException("Music not found!");
    }
    public Playlists getPlaylistById(int id) throws NotFoundException {
        try {
            Optional<Playlists> playlist = playlistRepository.findById(id);
            List<MusicDTO> musicDTOList = new ArrayList<>();

            if (playlist.isPresent()) {
                for (Music music: playlist.get().getMusics()) {
                    MusicDTO musicDTO = new MusicDTO(music);
                    musicDTOList.add(musicDTO);
                }
            } else {
                throw new NotFoundException("Artist not found!");
            }

            playlist.get().setMusicDTOList(musicDTOList);
            playlist.get().setMusics(null);
            return playlist.get();
        } catch (Exception e) {
            return null;
        }
    }

    public Playlists savePlaylist(Playlists playlists) {
//        if (playlists.getId()==null) {
//            playlists.setId((int) playlistRepository.count() + 1);
//        }
        return playlistRepository.save(playlists);
    }

    public void updatePlaylist(int id, Playlists playlists) {
//        if (playlists.getId()==null) {
//            playlists.setId((int) playlistRepository.count() + 1);
//        }
        Playlists updatingObject = playlistRepository.findById(id).get();

        if (updatingObject.getName() != null) {
            if (playlists.getName() != null){
                updatingObject.setName(playlists.getName());
            }
            if (playlists.getImg_link() != null){
                updatingObject.setImg_link(playlists.getImg_link());
            }
        }

        playlistRepository.save(updatingObject);
    }

    public void deletePlaylist(int id) {
        playlistRepository.deleteById(id);
    }

    public void addMusicToPlaylist(int playlistId, String musicId) throws NotFoundException {
        try {
            Playlists playlist = playlistRepository.findById(playlistId).get();
            Music music = musicRepository.findById(musicId).get();
            ArrayList<Music> p_musics = (ArrayList<Music>) playlist.getMusics();
            p_musics.add(music);

            playlist.setMusics(p_musics);

            playlistRepository.save(playlist);
        } catch (Exception e) {
            throw new NotFoundException("Data Not Found!");
        }
    }

    public List<Playlists> getPlaylistByUserId(Long id) {
        Optional<User> currUser = userRepository.findById(id);
        List<Playlists> playlists = new ArrayList<>();
        if (currUser.isPresent() && currUser.get().getPlaylists().size() > 0) {
            playlists = currUser.get().getPlaylists();
        }
        return playlists;
    }

    public List<Artist> getArtistsByUserId(Long id) {
        Optional<User> currUser = userRepository.findById(id);
        List<Artist> artists = new ArrayList<>();
        if (currUser.isPresent() && currUser.get().getArtists().size() > 0) {
            artists = currUser.get().getArtists();
        }
        return artists;
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class PostResponse {
    private List<Playlists> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class MusicResponse {
    private List<Music> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}