package kz.iitu.tynda.repository;

import kz.iitu.tynda.models.Artist;
import kz.iitu.tynda.models.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, String> {
  Optional<Artist> findByName(String name);
}
