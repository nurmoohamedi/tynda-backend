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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_id")
    private Integer id;
    private String name;
    private String duration;
    private String year;
    private String img_link;

    @ManyToMany(mappedBy = "musics")
    @JsonBackReference
    private List<Artist> artists = new ArrayList<>();

    public Music() {}
}