package tr.org.ji.pokedex.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tr.org.ji.pokedex.model.SaveUserRequestDTO;
import tr.org.ji.pokedex.model.UpdatePasswordRequestDTO;
import tr.org.ji.pokedex.model.UpdateRoleRequestDTO;
import tr.org.ji.pokedex.model.UserResponseDTO;
import tr.org.ji.pokedex.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable long id){
        LOGGER.info("A get user request has been sent with {} from an admin.", id);
        UserResponseDTO userResponseDTO = userService.getUser(id);
        return ResponseEntity.ok(userResponseDTO);
    }


    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> searchAllUsers(
                                                                @RequestParam(name = "name", defaultValue = "") String username){
        LOGGER.info("A search request has been sent with username: {} from an admin.", username);
        List<UserResponseDTO> userResponseDTOPage = userService.searchUser(username);
        return ResponseEntity.ok(userResponseDTOPage);
    }

    @GetMapping("/all-pagination")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<UserResponseDTO>> searchAllUsersWithPagination(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                                                @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                                @RequestParam(name = "name") String username){
        LOGGER.info("A search request has been sent with username: {} from an admin.", username);
        Page<UserResponseDTO> userResponseDTOPage = userService.searchUserWithPagination(pageNumber, pageSize, username);
        return ResponseEntity.ok(userResponseDTOPage);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_TRAINER', 'ROLE_ADMIN') ")
    public ResponseEntity<UserResponseDTO> getUser(){
        LOGGER.info("A get user information request has been sent");
        UserResponseDTO userResponseDTOPage = userService.getUserInfo();
        return ResponseEntity.ok(userResponseDTOPage);
    }

    @GetMapping("/with-role")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<UserResponseDTO>> getUserByRole(@RequestParam(name = "role", defaultValue = "USER") String role,
                                                               @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                                               @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                               @RequestParam(name = "name", defaultValue = "") String username){
        LOGGER.info("A get by role request has been sent with {}", role);
        Page<UserResponseDTO> userResponseDTOPage = userService.getUsersByRole(pageNumber, pageSize, username, "ROLE_" + role);
        return ResponseEntity.ok(userResponseDTOPage);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> saveUser(@Valid @RequestBody SaveUserRequestDTO dto){
        LOGGER.info("An user register request has been sent with name: {}", dto.getName());
        UserResponseDTO userResponseDTO = userService.saveUser(dto);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_TRAINER', 'ROLE_ADMIN') ")
    public ResponseEntity<UserResponseDTO> updatePassword(@Valid @RequestBody UpdatePasswordRequestDTO dto){
        LOGGER.info("An update password request has been sent");
        UserResponseDTO userResponseDTO = userService.updatePassword(dto);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') ")
    public ResponseEntity<UserResponseDTO> updateRole(@PathVariable long id, @Valid @RequestBody UpdateRoleRequestDTO dto){
        LOGGER.info("An update role request has been sent");
        UserResponseDTO userResponseDTO = userService.updateRole(id, dto);
        return ResponseEntity.ok(userResponseDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponseDTO> deleteUser(@PathVariable long id){
        LOGGER.info("A delete request has been sent");
        UserResponseDTO userResponseDTO = userService.deleteUser(id);
        return ResponseEntity.ok(userResponseDTO);
    }


}
