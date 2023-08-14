package tr.org.ji.pokedex.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tr.org.ji.pokedex.entity.Pokemon;
import tr.org.ji.pokedex.entity.PokemonType;
import tr.org.ji.pokedex.entity.User;
import tr.org.ji.pokedex.model.ListRequestDTO;
import tr.org.ji.pokedex.model.ListResponseDTO;
import tr.org.ji.pokedex.model.PokemonResponseDTO;
import tr.org.ji.pokedex.repository.PokemonRepository;
import tr.org.ji.pokedex.repository.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ListService {
    private final PokemonRepository pokemonRepository;
    private final UserRepository userRepository;

    public ListService(PokemonRepository pokemonRepository, UserRepository userRepository) {
        this.pokemonRepository = pokemonRepository;
        this.userRepository = userRepository;
    }

    public List<PokemonResponseDTO> getWishList() {
        User user = getUserByName();
        return user.getWishList().stream().map(pokemon -> {
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
            return pokemonResponseDTO;}).toList();
    }

    public List<PokemonResponseDTO> getCatchList() {
        User user = getUserByName();
        return user.getCatchList().stream().map(pokemon -> {
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
            return pokemonResponseDTO;}).toList();
    }

    public ListResponseDTO addPokemonToCatchList(ListRequestDTO dto) {
        User user = getUserByName();
        Pokemon pokemon = getPokemonById(dto);
        Set<Pokemon> newCatchList = user.getCatchList();
        newCatchList.add(pokemon);
        user.setCatchList(newCatchList);
        User savedUser = userRepository.save(user);
        return generateResponse(pokemon, savedUser);
    }


    public ListResponseDTO removePokemonFromCatchList(long id) {
        User user = getUserByName();
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new BadCredentialsException("Bad Credentials"));
        Set<Pokemon> newCatchList = user.getCatchList();
        newCatchList = newCatchList.stream().filter(pokemon1 -> pokemon1.getId() != id).collect(Collectors.toSet());
        user.setCatchList(newCatchList);
        User savedUser = userRepository.save(user);
        return generateResponse(pokemon, savedUser);
    }

    public ListResponseDTO addPokemonToWishList(ListRequestDTO dto) {
        User user = getUserByName();
        Pokemon pokemon = getPokemonById(dto);
        Set<Pokemon> newWishList = user.getWishList();
        newWishList.add(pokemon);
        user.setWishList(newWishList);
        User savedUser = userRepository.save(user);
        return generateResponse(pokemon, savedUser);
    }

    public ListResponseDTO removePokemonFromWishList(long id) {
        User user = getUserByName();
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new BadCredentialsException("Bad Credentials"));
        Set<Pokemon> newWishList = user.getWishList();
        newWishList = newWishList.stream().filter(pokemon1 -> pokemon1.getId() != id).collect(Collectors.toSet());
        user.setWishList(newWishList);
        User savedUser = userRepository.save(user);
        return generateResponse(pokemon, savedUser);
    }

    private User getUserByName() {
        String name = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Principal::getName).orElseThrow(() -> new BadCredentialsException("Bad Credentials"));
        return userRepository.findByUsername(name);
    }

    private Pokemon getPokemonById(ListRequestDTO dto) {
        return pokemonRepository.findById(dto.getPokemonId()).orElseThrow(() -> new BadCredentialsException("Bad Credentials"));
    }

    private ListResponseDTO generateResponse(Pokemon pokemon, User user) {
        ListResponseDTO listResponseDTO = new ListResponseDTO();
        listResponseDTO.setPokemonId(pokemon.getId());
        listResponseDTO.setPokemonName(pokemon.getName());
        listResponseDTO.setUserId(user.getId());
        listResponseDTO.setUserName(user.getUsername());
        return listResponseDTO;
    }

}
