package tr.org.ji.pokedex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.org.ji.pokedex.entity.PokemonType;
import tr.org.ji.pokedex.entity.Role;

import java.util.Optional;

public interface TypeRepository extends JpaRepository<PokemonType, Long> {
    boolean existsByName(String name);
    Optional<PokemonType> findByName(String name);
}
