package tr.org.ji.pokedex.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import tr.org.ji.pokedex.controller.UserController;
import tr.org.ji.pokedex.entity.Pokemon;
import tr.org.ji.pokedex.entity.PokemonType;
import tr.org.ji.pokedex.entity.Role;
import tr.org.ji.pokedex.repository.RoleRepository;
import tr.org.ji.pokedex.repository.TypeRepository;

@Component
public class DataLoader implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final RoleRepository roleRepository;
    private final TypeRepository typeRepository;

    // User Roles
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_TRAINER = "ROLE_TRAINER";

    // Pokemon Types
    private static final String ATTACKER = "attacker";
    private static final String SPEEDSTER = "speedster";
    private static final String ALL_ROUNDER = "all rounder";
    private static final String DEFENDER = "defender";
    private static final String SUPPORTER = "supporter";

    public DataLoader(RoleRepository roleRepository, TypeRepository typeRepository) {
        this.roleRepository = roleRepository;
        this.typeRepository = typeRepository;
    }

    private void loadPokemonType(String name) {
        if (!typeRepository.existsByName(name)) {
            PokemonType pokemonType = new PokemonType();
            pokemonType.setName(name);
            typeRepository.save(pokemonType);
        }
    }

    private void loadUserRole(String name) {
        if (!roleRepository.existsByName(name)) {
            Role userRole = new Role();
            userRole.setName(name);
            roleRepository.save(userRole);
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOGGER.info("Data loader is running...");
        loadUserRole(ROLE_ADMIN);
        loadUserRole(ROLE_TRAINER);

        loadPokemonType(ATTACKER);
        loadPokemonType(SPEEDSTER);
        loadPokemonType(ALL_ROUNDER);
        loadPokemonType(DEFENDER);
        loadPokemonType(SUPPORTER);
    }
}
