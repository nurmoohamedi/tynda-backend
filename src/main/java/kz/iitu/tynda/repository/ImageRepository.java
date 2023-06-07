package kz.iitu.tynda.repository;

import kz.iitu.tynda.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, String> {
    Optional<Image> findByName(String name);
}
