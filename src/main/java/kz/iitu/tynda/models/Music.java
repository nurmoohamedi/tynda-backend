package kz.iitu.tynda.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "music")
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_id")
    private Integer id;
    private String name;
    private String author;
    private String artist;
    private String duration;
    private String img_link;

    public Music() {}
    public Music(Integer id, String name, String author, String artist, String duration) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.artist = artist;
        this.duration = duration;
    }
}