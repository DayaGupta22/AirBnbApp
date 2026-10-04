package com.DayaGupta.Project.AirBnbApp.service;

import com.DayaGupta.Project.AirBnbApp.entities.User;
import com.DayaGupta.Project.AirBnbApp.exceptions.ResourceNotFoundException;
import com.DayaGupta.Project.AirBnbApp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    public User getuserById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found with id : {}"+id));
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("Username not found  on user service:{}"+username));
    }
}
