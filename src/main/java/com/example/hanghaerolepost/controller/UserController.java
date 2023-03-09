package com.example.hanghaerolepost.controller;

import com.example.hanghaerolepost.dto.LoginRequestDto;
import com.example.hanghaerolepost.dto.SignupRequestDto;
import com.example.hanghaerolepost.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "회원가입", notes = "회원가입 한다.")
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignupRequestDto signupRequestDto, BindingResult result) {
        if (result.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getFieldError().getDefaultMessage());
        }
        return userService.signup(signupRequestDto);
    }

    @ApiOperation(value = "로그인", notes = "로그인 한다.")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }
}
