package kz.iitu.tynda.models;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "audiobook")
public class Audiobook {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "audiobook_id")
    private String id;

    private String name;
    private String author;
    private String dictor;

    @Column(length = 1000)
    private String description;
    private String img_link;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(	name = "audiobook_musics",
            joinColumns = @JoinColumn(name = "audiobook_id"),
            inverseJoinColumns = @JoinColumn(name = "music_id"))
    private List<Music> musics = new ArrayList<>();

    public Audiobook() {}

    public Audiobook(String id, String name, ArrayList<Music> musics) {
        this.id = id;
        this.name = name;
        this.musics = musics;
    }
}