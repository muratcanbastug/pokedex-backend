package tr.org.ji.pokedex.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tr.org.ji.pokedex.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Page<User> findByUsernameContainsAndActiveIsTrueOrderByUsername(Pageable pageable, String name);
    List<User> findAllByUsernameContainsAndActiveIsTrueOrderByUsername(String name);
    Page<User> findByRoles_NameAndUsernameContainsAndActiveIsTrueOrderByUsername(Pageable pageable, String role, String name);
}
