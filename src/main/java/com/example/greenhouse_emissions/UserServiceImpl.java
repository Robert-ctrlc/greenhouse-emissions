package com.example.greenhouse_emissions;

import org.springframework.stereotype.Service;

import com.example.greenhouse_emissions.repository.UserRepository;

@Service
public class UserServiceImpl  implements UserService{
    
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user){
        if(userRepository.findByUsername(user.getUsername()) != null){
            throw new RuntimeException("User already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public boolean authenticateUser(String username, String password){
        User user = userRepository.findByUsername(username);
        return user !=null && user.getPassword().equals(password);
    }
}
