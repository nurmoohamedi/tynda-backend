package kz.iitu.tynda.repository;

import kz.iitu.tynda.models.Audiobook;
import kz.iitu.tynda.models.Playlists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudiobookRepository extends JpaRepository<Audiobook, String> {

}
