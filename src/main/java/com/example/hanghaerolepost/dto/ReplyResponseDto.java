package com.example.hanghaerolepost.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReplyResponseDto {
    private String reply;
    private String username;

    public ReplyResponseDto(String reply, String username) {
        this.reply = reply;
        this.username = username;
    }
}
