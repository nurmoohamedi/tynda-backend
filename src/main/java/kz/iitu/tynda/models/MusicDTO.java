package kz.iitu.tynda.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MusicDTO {
    private String id;
    private String name;
    private Long duration;
    private String img_link;
    private List<ArtistDTO> artists = new ArrayList<>();

    public MusicDTO() {}

    public MusicDTO(Music music) {
        this.id = music.getId();
        this.name = music.getName();
        this.duration = music.getDuration();
        this.img_link = music.getImg_link();
        for(Artist artist: music.getArtists()) {
           ArtistDTO artistDTO = new ArtistDTO();
           artistDTO.setId(artist.getId());
           artistDTO.setName(artist.getName());
           this.artists.add(artistDTO);
        }
    }
}
