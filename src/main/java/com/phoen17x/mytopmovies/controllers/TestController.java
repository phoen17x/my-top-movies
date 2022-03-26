package com.phoen17x.mytopmovies.controllers;

import com.phoen17x.mytopmovies.models.User;
import com.phoen17x.mytopmovies.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Value("${phoen17x.app.imdbApiKey}")
    private String imdbApiKey;
    private final WebClient client;
    private final UserRepository userRepository;

    @Autowired
    public TestController(WebClient client, UserRepository userRepository) {
        this.client = client;
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Optional<User> userAccess(Authentication authentication) {
        return userRepository.findByUsername(authentication.getName());
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @GetMapping("/movies/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String getMovie(@PathVariable("id") String id) {

        return client.get()
                .uri(String.format("/en/API/Title/%s/%s", imdbApiKey, id))
                .retrieve()
                .bodyToMono(String.class).block();
    }

}
