package com.DayaGupta.Project.AirBnbApp.security;

import com.DayaGupta.Project.AirBnbApp.dto.LoginDto;
import com.DayaGupta.Project.AirBnbApp.dto.SignUpRequestDto;
import com.DayaGupta.Project.AirBnbApp.dto.UserDto;
import com.DayaGupta.Project.AirBnbApp.entities.User;
import com.DayaGupta.Project.AirBnbApp.entities.enums.Role;
import com.DayaGupta.Project.AirBnbApp.exceptions.ResourceNotFoundException;
import com.DayaGupta.Project.AirBnbApp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;


   public UserDto signUp(SignUpRequestDto signUpRequestDto){
        User user = userRepository.findByEmail(signUpRequestDto.getEmail()).orElse(null);
        if(user != null){
            throw new RuntimeException("User already exists with same email id");
        }
        User newUser =modelMapper.map(signUpRequestDto,User.class);
        newUser.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        newUser.setRoles(Set.of(Role.GUEST));
        newUser = userRepository.save(newUser);
        return modelMapper.map(newUser,UserDto.class);
   }

   public String[] login(LoginDto loginDto){
      Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
               loginDto.getEmail(),loginDto.getPassword()
       ));
      User user = (User) authentication.getPrincipal();
        String arr[] = new String[2];
      String accessToken = jwtService.generateAccessToken(user);
      String refreshToken = jwtService.generateRefreshToken(user);
      arr[0]=accessToken;
      arr[1]=refreshToken;

      return arr;
   }
   public String refreshToken(String refreshToken){
       Long id = jwtService.generateUserIdFromToken(refreshToken);

       User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not foound in token "));
       return jwtService.generateAccessToken(user);
   }

}
