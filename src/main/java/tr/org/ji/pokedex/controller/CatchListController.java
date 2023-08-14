package tr.org.ji.pokedex.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tr.org.ji.pokedex.model.ListResponseDTO;
import tr.org.ji.pokedex.model.ListRequestDTO;
import tr.org.ji.pokedex.model.PokemonResponseDTO;
import tr.org.ji.pokedex.service.ListService;
import tr.org.ji.pokedex.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/catch-lists")
public class CatchListController {
    private final ListService listService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    public CatchListController(ListService listService) {
        this.listService = listService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRAINER')")
    public ResponseEntity<List<PokemonResponseDTO>> getList() {
        LOGGER.info("A get catch list request has been sent.");
        List<PokemonResponseDTO> pokemonResponseDTOS = listService.getCatchList();
        return ResponseEntity.ok(pokemonResponseDTOS);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRAINER')")
    public ResponseEntity<ListResponseDTO> addCatchList(@Valid @RequestBody ListRequestDTO dto) {
        LOGGER.info("An add pokemon to catch list request has been sent.");
        ListResponseDTO addCatchListResponseDTO = listService.addPokemonToCatchList(dto);
        return ResponseEntity.ok(addCatchListResponseDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRAINER')")
    public ResponseEntity<ListResponseDTO> removeCatchList(@PathVariable long id) {
        LOGGER.info("A remove pokemon from catch list request has been sent.");
        ListResponseDTO addCatchListResponseDTO = listService.removePokemonFromCatchList(id);
        return ResponseEntity.ok(addCatchListResponseDTO);
    }
}
