package com.example.hanghaerolepost.dto;

import com.example.hanghaerolepost.entity.Reply;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReplyResponseDto {
    private Long id;
    private String reply;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String username;

    public ReplyResponseDto(Reply reply) {
        this.id = reply.getId();
        this.reply = reply.getReply();
        this.createdAt = reply.getCreatedAt();
        this.modifiedAt = reply.getModifiedAt();
        this.username = reply.getUser().getUsername();
    }

    public ReplyResponseDto(Reply reply, String username) {
        this.id = reply.getId();
        this.reply = reply.getReply();
        this.createdAt = reply.getCreatedAt();
        this.modifiedAt = reply.getModifiedAt();
        this.username = username;
    }

}