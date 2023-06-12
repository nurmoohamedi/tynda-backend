package kz.iitu.tynda.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "music")
public class Music {

    @Id
    @Column(name = "music_id")
    private String id;
    private String name;
    private Long duration;

    private String img_link;
    private String local_path;
    private String public_path;

    @ManyToMany(mappedBy = "musics")
    @JsonBackReference
    private List<Artist> artists = new ArrayList<>();

    public Music() {}

    public Music(String id, String name, Long duration, String img_link, String local_path, String public_path, List<Artist> artists) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.img_link = img_link;
        this.local_path = local_path;
        this.public_path = public_path;
        this.artists = artists;
    }
}