package com.example.hanghaerolepost.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ResultResponseDto {
    private HttpStatus status;
    private String message;

    public ResultResponseDto(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}