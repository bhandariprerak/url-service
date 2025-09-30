package com.url.shortener.urlservice.service;

import com.url.shortener.urlservice.models.User;
import com.url.shortener.urlservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Service
@AllArgsConstructor
/**
 * Service class for retrieving user information by username.
 */
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().replace("ROLE_", "")) // strips "ROLE_" prefix
                .build();
    }

    /**
     * Finds a user by username.
     *
     * @param name The username to search for
     * @return The user entity if found
     * @throws UsernameNotFoundException if no user is found with the given username
     */
    public User findByUsername(String name) {
        // Retrieve user or throw exception if not found
        return userRepository.findByUsername(name).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + name)
        );
    }
}