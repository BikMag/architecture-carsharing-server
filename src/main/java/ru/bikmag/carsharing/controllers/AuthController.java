package ru.bikmag.carsharing.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.bikmag.carsharing.models.User;
import ru.bikmag.carsharing.repositories.UserRepository;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "Error: Username already exists!";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of("USER")); // Все новые пользователи получают роль USER
        userRepository.save(user);
        return "User registered successfully!";
    }

    @GetMapping("/check")
    public String checkUser() {
        return "User logged in successfully!";
    }
}
