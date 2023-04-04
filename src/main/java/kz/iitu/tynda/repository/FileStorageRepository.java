package kz.iitu.tynda.repository;

import kz.iitu.tynda.models.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileStorageRepository extends JpaRepository<Song, String> {



}
