package hello.jpa.dao;

import hello.jpa.model.HeroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface HeroDAO extends JpaRepository<HeroEntity, Long>{
    Long countByNameAndPseudonym(String name, String pseudonym);
    Optional<HeroEntity> findByName(String name);
}
