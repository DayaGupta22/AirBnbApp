package com.DayaGupta.Project.AirBnbApp.controller;

import com.DayaGupta.Project.AirBnbApp.dto.LoginDto;
import com.DayaGupta.Project.AirBnbApp.dto.LoginResponseDto;
import com.DayaGupta.Project.AirBnbApp.dto.SignUpRequestDto;
import com.DayaGupta.Project.AirBnbApp.dto.UserDto;
import com.DayaGupta.Project.AirBnbApp.security.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignUpRequestDto signUpRequestDto){
       return new ResponseEntity<>(authService.signUp(signUpRequestDto),HttpStatus.CREATED);

    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto , HttpServletRequest request, HttpServletResponse response){
        String[] token = authService.login(loginDto);
        Cookie cookie= new Cookie("refresh_Token",token[1]);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(new LoginResponseDto(token[0],"User Logged in succesfully"));

    }
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request){
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie ->"refresh_Token".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(()-> new AuthenticationServiceException("refresh token not found inside the cookies"));
        String accessToken = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(new LoginResponseDto(accessToken,"refresh a token"));

    }

}
