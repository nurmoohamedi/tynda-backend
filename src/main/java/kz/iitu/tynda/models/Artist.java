package kz.iitu.tynda.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "table_artist")
public class Artist {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "artist_id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private String date;

    @Column(name = "followers")
    private Integer followers;

    @Column(name = "listeners")
    private Integer listeners;

    @Column(name = "img_link")
    private String img_link;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "artist_musics",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "music_id"))
    private List<Music> musics = new ArrayList<>();
}
