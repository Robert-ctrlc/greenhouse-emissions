package com.example.greenhouse_emissions;



public interface UserService {
      User saveUser(User user);
        boolean authenticateUser(String username, String password);
    
}