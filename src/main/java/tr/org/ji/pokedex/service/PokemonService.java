package tr.org.ji.pokedex.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tr.org.ji.pokedex.entity.Pokemon;
import tr.org.ji.pokedex.entity.PokemonType;
import tr.org.ji.pokedex.model.PokemonResponseDTO;
import tr.org.ji.pokedex.model.SaveUpdatePokemonRequestDTO;
import tr.org.ji.pokedex.repository.PokemonRepository;
import tr.org.ji.pokedex.repository.TypeRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PokemonService {
    private final PokemonRepository pokemonRepository;
    private final TypeRepository typeRepository;

    public PokemonService(PokemonRepository pokemonRepository, TypeRepository typeRepository) {
        this.pokemonRepository = pokemonRepository;
        this.typeRepository = typeRepository;
    }

    public PokemonResponseDTO getPokemon(long id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No pokemon with given id."));
        return generateResponse(pokemon);
    }

    public PokemonResponseDTO savePokemon(SaveUpdatePokemonRequestDTO dto) {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(dto.getName());
        pokemon.setDifficulty(dto.getDifficulty());
        pokemon.setEndurance(dto.getEndurance());
        pokemon.setMobility(dto.getMobility());
        pokemon.setOffense(dto.getOffense());
        pokemon.setDescription(dto.getDescription());
        pokemon.setSupport(dto.getSupport());
        pokemon.setScoring(dto.getScoring());

        Set<PokemonType> types = new java.util.HashSet<>(Set.of());
        for (int i = 0; i <(Math.min(dto.getTypes().size(), 2)); i++) {
            PokemonType pokemonType = typeRepository.findByName(dto.getTypes().get(i)).orElseThrow(() -> new BadCredentialsException("Given pokemon type does not exist."));
            types.add(pokemonType);
        }
        pokemon.setTypes(types);
        Pokemon savedPokemon = pokemonRepository.save(pokemon);
        return generateResponse(savedPokemon);
    }

    public PokemonResponseDTO updatePokemon(SaveUpdatePokemonRequestDTO dto, Long id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No pokemon with given id."));
        pokemon.setName(dto.getName());
        pokemon.setDifficulty(dto.getDifficulty());
        pokemon.setEndurance(dto.getEndurance());
        pokemon.setMobility(dto.getMobility());
        pokemon.setOffense(dto.getOffense());
        pokemon.setDescription(dto.getDescription());
        pokemon.setSupport(dto.getSupport());
        pokemon.setScoring(dto.getScoring());

        Set<PokemonType> types = new java.util.HashSet<>(Set.of());
        for (int i = 0; i < 2; i++) {
            PokemonType pokemonType = typeRepository.findByName(dto.getTypes().get(i)).orElseThrow(() -> new BadCredentialsException("Given pokemon type does not exist."));
            types.add(pokemonType);
        }
        pokemon.setTypes(types);
        Pokemon savedPokemon = pokemonRepository.save(pokemon);
        return generateResponse(savedPokemon);
    }

    public PokemonResponseDTO deletePokemon(long id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No pokemon with given id."));
        pokemon.setActive(false);
        Pokemon savedPokemon = pokemonRepository.save(pokemon);
        return generateResponse(savedPokemon);
    }

    public Page<PokemonResponseDTO> searchPokemon(int pageNumber, int pageSize, String pokemonName) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Pokemon> page = pokemonRepository.findByNameContainsAndActiveIsTrueOrderByName(pageable, pokemonName);
        List<PokemonResponseDTO> pokemonResponseDTOS = page.stream().map(this::generateResponse).toList();
        return new PageImpl<>(pokemonResponseDTOS, pageable, page.getTotalElements());
    }

    public Page<PokemonResponseDTO> getPokemonsByTypePage(int pageNumber, int pageSize, String pokemonName, String type) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        String[] types = type.split(",");
        Page<Pokemon> page;
        page = pokemonRepository.findAllByTypes_NameInAndNameContainsAndActiveIsTrueOrderByName(pageable, types, pokemonName);
        List<PokemonResponseDTO> pokemonResponseDTOS = page.stream().map(this::generateResponse).toList();
        return new PageImpl<>(pokemonResponseDTOS, pageable, page.getTotalElements());
    }

    public List<PokemonResponseDTO> getPokemonsByType(String pokemonName, String type) {
        if("ALL TYPES".equals(type)) {
            return pokemonRepository.findByNameContainsAndActiveIsTrueOrderByName(pokemonName)
                    .stream().map(this::generateResponse).toList();
        }
        return pokemonRepository.findAllByTypes_NameAndNameContainsAndActiveIsTrueOrderByName(type, pokemonName)
                .stream().map(this::generateResponse).toList();
    }

    private PokemonResponseDTO generateResponse(Pokemon pokemon) {
        PokemonResponseDTO pokemonResponseDTO = new PokemonResponseDTO();
        pokemonResponseDTO.setName(pokemon.getName());
        pokemonResponseDTO.setId(pokemon.getId());
        pokemonResponseDTO.setDescription(pokemon.getDescription());
        pokemonResponseDTO.setDifficulty(pokemon.getDifficulty());
        pokemonResponseDTO.setEndurance(pokemon.getEndurance());
        pokemonResponseDTO.setMobility(pokemon.getMobility());
        pokemonResponseDTO.setOffense(pokemon.getOffense());
        pokemonResponseDTO.setSupport(pokemon.getSupport());
        pokemonResponseDTO.setScoring(pokemon.getScoring());
        pokemonResponseDTO.setTypes(pokemon.getTypes().stream().map(PokemonType::getName).collect(Collectors.toSet()));
        return pokemonResponseDTO;
    }
}
