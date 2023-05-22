package kz.iitu.tynda.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ArtistDTO {
    private String id;
    private String name;

//    private List<MusicDTO> musics = new ArrayList<>();

    public ArtistDTO() {
    }

//    public ArtistDTO(Artist artist) {
//        this.id = artist.getId();
//        this.name = artist.getName();
//
//        for (Music music: artist.getMusics()) {
//            MusicDTO musicDTO = new MusicDTO();
//            musicDTO.setId(music.getId());
//            musicDTO.setName(music.getName());
//            musicDTO.setDuration(music.getDuration());
//            musicDTO.setImg_link(music.getImg_link());
//            this.musics.add(musicDTO);
//        }
//    }
}
