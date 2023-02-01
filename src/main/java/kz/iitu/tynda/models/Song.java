package kz.iitu.tynda.models;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "uploader")
    private String uploader;

    @Column(name = "size")
    private double size;

    @Column(name = "date")
    private String date;

    @Column(name = "path")
    private String path;

    public Song() {}

    public Song(String name, double size, String date, String path) {
        this.name = name;
        this.size = size;
        this.date = date;
        this.path = path;
    }

    public Song(String id, String name, double size, String date, String path) {
        this.id = id;
        this.name = name;
        this.uploader = uploader;
        this.size = size;
        this.date = date;
        this.path = path;
    }
}
