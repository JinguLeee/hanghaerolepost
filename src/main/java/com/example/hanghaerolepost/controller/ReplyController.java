package com.example.hanghaerolepost.controller;

import com.example.hanghaerolepost.dto.ReplyRequestDto;
import com.example.hanghaerolepost.dto.ReplyResponseDto;
import com.example.hanghaerolepost.service.ReplyService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply")
public class ReplyController {
    private final ReplyService replyService;

    @ApiOperation(value = "댓글 등록", notes = "게시글의 댓글을 등록한다.")
    @PostMapping("/{postId}")
    public ReplyResponseDto createReply(@PathVariable Long postId, @RequestBody ReplyRequestDto replyRequestDto, HttpServletRequest request) {
        return replyService.createReply(postId, replyRequestDto, request);
    }

    @ApiOperation(value = "댓글 수정", notes = "자신이 쓴 댓글 중 선택한 댓글을 수정한다.")
    @PutMapping("/{replyId}")
    public ReplyResponseDto updateReply(@PathVariable Long replyId, @RequestBody ReplyRequestDto replyRequestDto, HttpServletRequest request) {
        return replyService.update(replyId, replyRequestDto, request);
    }

    @ApiOperation(value = "댓글 삭제", notes = "자신이 쓴 댓글 중 선택한 댓글을 삭제한다.")
    @DeleteMapping("/{replyId}")
    public ResponseEntity<String> deleteReply(@PathVariable Long replyId, HttpServletRequest request) {
        return replyService.delete(replyId, request);
    }
}
