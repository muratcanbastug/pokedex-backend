package tr.org.ji.pokedex.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tr.org.ji.pokedex.entity.Pokemon;

import java.util.List;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    List<Pokemon> findAll();
    Page<Pokemon> findByNameContainsAndActiveIsTrueOrderByName(Pageable pageable, String name);
    Page<Pokemon> findAllByTypes_NameInAndNameContainsAndActiveIsTrueOrderByName(Pageable pageable, String[] types, String name);
    List<Pokemon> findAllByTypes_NameAndNameContainsAndActiveIsTrueOrderByName(String type, String name);
    List<Pokemon> findByNameContainsAndActiveIsTrueOrderByName(String name);
}
