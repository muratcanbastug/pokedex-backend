package tr.org.ji.pokedex.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tr.org.ji.pokedex.model.PokemonResponseDTO;
import tr.org.ji.pokedex.model.SaveUpdatePokemonRequestDTO;
import tr.org.ji.pokedex.service.PokemonService;

import java.util.List;

@RestController
@RequestMapping("/pokemons")
public class PokemonController {
    private final PokemonService pokemonService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRAINER')")
    public ResponseEntity<List<PokemonResponseDTO>> getAllPokemons( @RequestParam(name = "type", defaultValue = "ALL TYPES") String type,
                                                                   @RequestParam(name = "name", defaultValue = "") String pokemonName){
        LOGGER.info("A get all pokemons request has been sent.");
        List<PokemonResponseDTO> pokemonResponseDTO = pokemonService.getPokemonsByType(pokemonName, type);
        return ResponseEntity.ok(pokemonResponseDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRAINER')")
    public ResponseEntity<PokemonResponseDTO> getPokemon(@PathVariable long id){
        LOGGER.info("A get pokemon request has been sent with {}.", id);
        PokemonResponseDTO pokemonResponseDTO = pokemonService.getPokemon(id);
        return ResponseEntity.ok(pokemonResponseDTO);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRAINER')")
    public ResponseEntity<Page<PokemonResponseDTO>> searchAllPokemon(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                                               @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                               @RequestParam(name = "name") String pokemonName){
        LOGGER.info("A search request has been sent with username: {} from an admin.", pokemonName);
        Page<PokemonResponseDTO> pokemonResponseDTOS = pokemonService.searchPokemon(pageNumber, pageSize, pokemonName);
        return ResponseEntity.ok(pokemonResponseDTOS);
    }

    @GetMapping("/with-type")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRAINER')")
    public ResponseEntity<Page<PokemonResponseDTO>> getPokemonByType(@RequestParam(name = "type", defaultValue = "") String type,
                                                               @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                                               @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                               @RequestParam(name = "name", defaultValue = "") String pokemonName){
        LOGGER.info("A get by type request has been sent with {}", type);
        Page<PokemonResponseDTO> pokemonResponseDTOS = pokemonService.getPokemonsByTypePage(pageNumber, pageSize, pokemonName, type);
        return ResponseEntity.ok(pokemonResponseDTOS);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PokemonResponseDTO> savePokemon(@Valid @RequestBody SaveUpdatePokemonRequestDTO dto){
        LOGGER.info("A pokemon register request has been sent with name: {}", dto.getName());
        PokemonResponseDTO pokemonResponseDTO = pokemonService.savePokemon(dto);
        return ResponseEntity.ok(pokemonResponseDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PokemonResponseDTO> updatePokemon(@PathVariable long id, @Valid @RequestBody SaveUpdatePokemonRequestDTO dto){
        LOGGER.info("A pokemon update request has been sent with name: {}", dto.getName());
        PokemonResponseDTO pokemonResponseDTO = pokemonService.updatePokemon(dto, id);
        return ResponseEntity.ok(pokemonResponseDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PokemonResponseDTO> deletePokemon(@PathVariable long id){
        LOGGER.info("A delete pokemon request has been sent");
        PokemonResponseDTO pokemonResponseDTO = pokemonService.deletePokemon(id);
        return ResponseEntity.ok(pokemonResponseDTO);
    }

}
