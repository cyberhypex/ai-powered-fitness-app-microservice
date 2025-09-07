package com.fitness.userService.service;


import com.fitness.userService.DTO.RegisterRequest;
import com.fitness.userService.DTO.UserResponse;
import com.fitness.userService.model.User;
import com.fitness.userService.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserResponse register(@Valid RegisterRequest request) {

        if(userRepository.existsByEmail(request.getEmail())){
            User existingUser= userRepository.findByEmail(request.getEmail());
            UserResponse userResponse=new UserResponse();
            userResponse.setId(existingUser.getId());
            userResponse.setPassword(existingUser.getPassword());
            userResponse.setEmail(existingUser.getEmail());
            userResponse.setKeycloakID(existingUser.getKeycloakID());
            userResponse.setFirstName(existingUser.getFirstName());
            userResponse.setLastName(existingUser.getLastName());
            userResponse.setCreatedAt(existingUser.getCreatedAt());
            userResponse.setUpdatedAt(existingUser.getUpdatedAt());
            return userResponse;
        }
        User user=new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
       User savedUser= userRepository.save(user);
       UserResponse userResponse=new UserResponse();
       userResponse.setId(user.getId());
       userResponse.setPassword(user.getPassword());
       userResponse.setEmail(user.getEmail());
        userResponse.setKeycloakID(savedUser.getKeycloakID());
       userResponse.setFirstName(user.getFirstName());
       userResponse.setLastName(user.getLastName());
       userResponse.setCreatedAt(user.getCreatedAt());
       userResponse.setUpdatedAt(user.getUpdatedAt());
       return userResponse;
    }

    public UserResponse getUserProfile(String userId) {
        User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        UserResponse userResponse=new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setPassword(user.getPassword());
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());
        return userResponse;
    }

    public boolean existByUserId(String userId) {
        return userRepository.existsById(userId);
    }

    public Boolean existsByKeyCloakId(String userId) {
        return null;
    }
}
