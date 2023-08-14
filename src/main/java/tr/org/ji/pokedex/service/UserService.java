package tr.org.ji.pokedex.service;

import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tr.org.ji.pokedex.entity.Pokemon;
import tr.org.ji.pokedex.entity.Role;
import tr.org.ji.pokedex.entity.User;
import tr.org.ji.pokedex.model.*;
import tr.org.ji.pokedex.repository.RoleRepository;
import tr.org.ji.pokedex.repository.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        Hibernate.initialize(user.getRoles());
        Hibernate.initialize(user.getWishList());
        Hibernate.initialize(user.getCatchList());
        return new MyUserDetail(user);
    }

    public UserResponseDTO saveUser(SaveUserRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        var roleUserOpt = roleRepository.findByName("ROLE_TRAINER");
        roleUserOpt.ifPresent((userRole) -> user.setRoles(Set.of(userRole)));
        user.setCatchList(Set.of());
        user.setWishList(Set.of());
        User savedUser = userRepository.save(user);
        return generateResponse(savedUser);
    }

    public UserResponseDTO updatePassword(UpdatePasswordRequestDTO dto) {
       String name = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Principal::getName).orElseThrow(() -> new BadCredentialsException("Bad Credentials"));

       User user = userRepository.findByUsername(name);
       if(passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
           user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
           User savedUser = userRepository.save(user);
           return generateResponse(savedUser);
       }
       throw new BadCredentialsException("Given password is not correct.");
    }

    public UserResponseDTO getUserInfo() {
        String name = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Principal::getName).orElseThrow(() -> new BadCredentialsException("Bad Credentials"));
        User user = userRepository.findByUsername(name);
        return generateResponse(user);
    }

    public UserResponseDTO getUser(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No user with given id."));
        return generateResponse(user);
    }

    public Page<UserResponseDTO> searchUserWithPagination(int pageNumber, int pageSize, String username) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = userRepository.findByUsernameContainsAndActiveIsTrueOrderByUsername(pageable, username);
        List<UserResponseDTO> userResponseDTOS = page.stream().map(this::generateResponse).toList();
        return new PageImpl<>(userResponseDTOS, pageable, page.getTotalElements());
    }

    public List<UserResponseDTO> searchUser(String username) {
        List<User> users = userRepository.findAllByUsernameContainsAndActiveIsTrueOrderByUsername(username);
        return users.stream().map(this::generateResponse).toList();

    }

    public Page<UserResponseDTO> getUsersByRole(int pageNumber, int pageSize, String username, String role) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = userRepository.findByRoles_NameAndUsernameContainsAndActiveIsTrueOrderByUsername(pageable, role, username);
        List<UserResponseDTO> userResponseDTOS = page.stream().map(this::generateResponse).toList();
        return new PageImpl<>(userResponseDTOS, pageable, page.getTotalElements());
    }

    public UserResponseDTO deleteUser(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Given id does not exist."));
        user.setActive(false);
        User savedUser = userRepository.save(user);
        return generateResponse(savedUser);
    }

    public UserResponseDTO updateRole(long id, UpdateRoleRequestDTO dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Given id does not exist."));
        Set<Role> roles = new java.util.HashSet<>(Set.of());
        for (int i = 0; i < (Math.min(dto.getNewRoles().size(), 2)); i++) {
            Role role = roleRepository.findByName(dto.getNewRoles().get(i)).orElseThrow(() -> new BadCredentialsException("Given role does not exist."));
            roles.add(role);
        }
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        return generateResponse(savedUser);
    }

    private UserResponseDTO generateResponse(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setName(user.getUsername());
        userResponseDTO.setId(user.getId());
        userResponseDTO.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
        userResponseDTO.setCatchList(user.getCatchList().stream().map(Pokemon::getName).collect(Collectors.toSet()));
        userResponseDTO.setWishList(user.getWishList().stream().map(Pokemon::getName).collect(Collectors.toSet()));
        return userResponseDTO;
    }

}
