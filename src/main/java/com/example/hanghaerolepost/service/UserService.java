package com.example.hanghaerolepost.service;

import com.example.hanghaerolepost.dto.LoginRequestDto;
import com.example.hanghaerolepost.dto.SignupRequestDto;
import com.example.hanghaerolepost.entity.User;
import com.example.hanghaerolepost.entity.UserRoleEnum;
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
        String password = signupRequestDto.getPassword();
        UserRoleEnum role = signupRequestDto.getRole();

        // 회원 중복 확인
        userRepository.findByUsername(username).ifPresent(it -> {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        });

        User user = new User(username, password, role);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("회원가입 완료");
    }

    public ResponseEntity<String> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 확인
        if(!user.getPassword().equals(password)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return ResponseEntity.status(HttpStatus.OK).body("로그인 완료");
    }
}