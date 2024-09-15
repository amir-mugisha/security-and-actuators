package org.example.product_management_system.service;

import org.example.product_management_system.model.User;
import org.example.product_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Cacheable(value = "users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void addUser(@RequestBody User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Cacheable(value = "users")
    public Optional<User> getUser(String userId){
        boolean exists = userRepository.existsById(userId);
        if(!exists){
            throw new IllegalStateException("User with id " + userId + " does not exist");
        }

        return userRepository.findById(userId);
    }

    @CacheEvict(value = "users", key = "#userId")
    public void deleteUser(String userId) throws IllegalAccessException {
        boolean exists = userRepository.existsById(userId);
        if(!exists){
            throw new IllegalAccessException("User with id " + userId + " does not exist");
        }
        userRepository.deleteById(userId);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void updateUser(String userId, String name, String password) throws IllegalStateException {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("User with id " + userId + " does not exist")
        );

        if (name != null && !name.isEmpty()) {
            user.setName(name);
        }

        if (password != null) {
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
        }
        userRepository.save(user);

    }

}
