package com.example.hanghaerolepost.service;

import com.example.hanghaerolepost.dto.LoginRequestDto;
import com.example.hanghaerolepost.dto.SignupRequestDto;
import com.example.hanghaerolepost.entity.User;
import com.example.hanghaerolepost.jwt.JwtUtil;
import com.example.hanghaerolepost.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public ResponseEntity<String> signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();

        // 회원 중복 확인
        userRepository.findByUsername(username).ifPresent(it -> {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        });

        userRepository.save(new User(username, signupRequestDto.getPassword(), signupRequestDto.getRole()));
        return ResponseEntity.status(HttpStatus.OK).body("회원가입 성공");
    }

    public ResponseEntity<String>  login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        // 사용자 확인
        User user = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 확인
        if(!user.getPassword().equals(loginRequestDto.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return ResponseEntity.status(HttpStatus.OK).body("로그인 성공");
    }
}