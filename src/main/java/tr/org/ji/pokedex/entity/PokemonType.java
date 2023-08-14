package tr.org.ji.pokedex.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "POKEMON_TYPE")
public class PokemonType extends EntityBase{
    @Column(name = "TYPE_NAME", length = 255, unique = true)
    private String name;

    @ManyToMany(mappedBy = "types")
    private Set<Pokemon> pokemons;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(Set<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }
}
