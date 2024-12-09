package com.example.greenhouse_emissions;

import com.example.greenhouse_emissions.User;
import com.example.greenhouse_emissions.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface UserService {

  User registerUser(User user); 

  User getUserById(Long id); 

  List<User> getAllUsers(); 

  User updateUser(Long id, User user); 

  void deleteUser(Long id); 

  boolean login(String username, String password);

  boolean isLoggedIn();

  void logout();
}
