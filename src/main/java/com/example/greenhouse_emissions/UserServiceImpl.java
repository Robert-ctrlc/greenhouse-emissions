package com.example.greenhouse_emissions;


import com.example.greenhouse_emissions.User;
import com.example.greenhouse_emissions.repository.UserRepository;
import com.example.greenhouse_emissions.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private boolean loggedIn = false;

    @Override
    public User registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
    return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = getUserById(id);
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    @Override
    public boolean login(String username, String password) {
    boolean isAuthenticated = userRepository.findByUsernameAndPassword(username, password).isPresent();
    if (isAuthenticated) {
        loggedIn = true;
    }
    return isAuthenticated;
    }

    @Override
    public boolean isLoggedIn() {
    System.out.println("Logged in status: " + loggedIn);
    return loggedIn;
    }

    public void logout() {
        loggedIn = false; 
    }

   
}
