package com.example.hanghaerolepost.controller;

import com.example.hanghaerolepost.dto.ReplyRequestDto;
import com.example.hanghaerolepost.dto.ReplyResponseDto;
import com.example.hanghaerolepost.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply")
public class ReplyController {
    private final ReplyService replyService;

    // 게시글 번호
    @PostMapping("/{id}")
    public ReplyResponseDto createReply(@PathVariable Long id, @RequestBody ReplyRequestDto replyRequestDto, HttpServletRequest request) {
        return replyService.createReply(id, replyRequestDto, request);
    }

    // 댓글 번호
    @PutMapping("/{id}")
    public ReplyResponseDto updateReply(@PathVariable Long id, @RequestBody ReplyRequestDto replyRequestDto, HttpServletRequest request) {
        return replyService.update(id, replyRequestDto, request);
    }

    // 댓글 번호
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReply(@PathVariable Long id, HttpServletRequest request) {
        return replyService.delete(id, request);
    }
}
