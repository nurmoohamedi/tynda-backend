package kz.iitu.tynda.models;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "playlist")
public class Playlists {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "playlist_id")
    private Integer id;

    private String name;
    private String img_link;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "playlist_music",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "music_id"))
    private List<Music> musics = new ArrayList<>();

    public Playlists() {}

    public Playlists(Integer id, String name, ArrayList<Music> musics) {
        this.id = id;
        this.name = name;
        this.musics = musics;
    }
}