package kz.iitu.tynda.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MusicDTO {
    private Integer id;
    private String name;
    private String duration;
    private String year;
    private String img_link;
    private List<ArtistDTO> artists = new ArrayList<>();

    public MusicDTO() {}

    public MusicDTO(Music music) {
        this.id = music.getId();
        this.name = music.getName();
        this.duration = music.getDuration();
        this.year = music.getYear();
        this.img_link = music.getImg_link();
        for(Artist artist: music.getArtists()) {
           ArtistDTO artistDTO = new ArtistDTO();
           artistDTO.setId(artist.getId());
           artistDTO.setName(artist.getName());
           this.artists.add(artistDTO);
        }
    }
}
