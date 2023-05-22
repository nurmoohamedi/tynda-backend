package kz.iitu.tynda.repository;

import java.util.List;
import java.util.Optional;

import kz.iitu.tynda.models.Playlists;
import kz.iitu.tynda.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

//  List<Playlists> find(Long id);
}
