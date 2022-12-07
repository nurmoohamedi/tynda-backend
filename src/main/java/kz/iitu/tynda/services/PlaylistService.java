package kz.iitu.tynda.services;

import kz.iitu.tynda.helpers.exception.NotFoundException;
import kz.iitu.tynda.models.Music;
import kz.iitu.tynda.models.Playlists;
import kz.iitu.tynda.repository.MusicRepository;
import kz.iitu.tynda.repository.PlaylistRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {

    PlaylistRepository playlistRepository;
    MusicRepository musicRepository;

    public PlaylistService(PlaylistRepository playlistRepository, MusicRepository musicRepository) {
        this.playlistRepository = playlistRepository;
        this.musicRepository = musicRepository;
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

    public Music getMusicById(int id) throws NotFoundException {
        Optional<Music> music = musicRepository.findById(id);

        if (music.isPresent())
            return music.get();
        else
            throw new NotFoundException("Music not found!");
    }
    public Playlists getPlaylistById(int id) throws NotFoundException {
        Optional<Playlists> music = playlistRepository.findById(id);

        if (music.isPresent())
            return music.get();
        else
            throw new NotFoundException("Playlist not found!");
    }

    public void savePlaylist(Playlists playlists) {
//        if (playlists.getId()==null) {
//            playlists.setId((int) playlistRepository.count() + 1);
//        }
        playlistRepository.save(playlists);
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