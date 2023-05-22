package kz.iitu.tynda.repository;

import kz.iitu.tynda.models.Artist;
import kz.iitu.tynda.models.Music;
import kz.iitu.tynda.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MusicRepository extends JpaRepository<Music, Integer> {
  Optional<Music> findByName(String name);
//  List<Artist> find
}
