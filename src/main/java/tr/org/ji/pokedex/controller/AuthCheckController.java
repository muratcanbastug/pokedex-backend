package tr.org.ji.pokedex.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth-check")
public class AuthCheckController {

    @GetMapping("/login")
    @PreAuthorize("hasAnyRole('ROLE_TRAINER', 'ROLE_ADMIN') ")
    public ResponseEntity<Boolean> loginCheck(){
        return ResponseEntity.ok(true);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> adminCheck(){
        return ResponseEntity.ok(true);
    }
}
