package com.example.hanghaerolepost.service;

import com.example.hanghaerolepost.dto.ReplyRequestDto;
import com.example.hanghaerolepost.dto.ReplyResponseDto;
import com.example.hanghaerolepost.entity.Post;
import com.example.hanghaerolepost.entity.Reply;
import com.example.hanghaerolepost.entity.User;
import com.example.hanghaerolepost.jwt.JwtUtil;
import com.example.hanghaerolepost.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final JwtUtil jwtUtil;
    private final CheckPost checkPost;

    public ReplyResponseDto createReply(Long id, ReplyRequestDto requestDto, HttpServletRequest request) {
        User user = jwtUtil.getUser(request); // 토큰이 있는 경우 사용자의 정보를 받아온다.
        Post post = checkPost.getPost(id);  // 게시글이 존재하는지 확인 후 가져온다
        Reply reply = replyRepository.saveAndFlush(new Reply(requestDto.getReply(), user, post));
        return new ReplyResponseDto(reply.getReply(), user.getUsername());
    }

    public ResponseEntity<String> update(Long id, ReplyRequestDto replyRequestDto, HttpServletRequest request) {
        User user = jwtUtil.getUser(request); // 토큰이 있는 경우 사용자의 정보를 받아온다.
        Reply reply = getReply(id); // 댓글이 존재하는지 확인 후 가져온다.
        checkPost.getReplyRole(id, user);  // 권한을 확인한다 (자신이 쓴 댓글인지 확인)
        reply.update(replyRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("댓글 수정 완료");
    }

    public ResponseEntity<String> delete(Long id, HttpServletRequest request) {
        User user = jwtUtil.getUser(request);      // 토큰이 있는 경우 사용자의 정보를 받아온다.
        getReply(id); // 댓글이 존재하는지 확인 후 가져온다.
        checkPost.getReplyRole(id, user);  // 권한을 확인한다 (자신이 쓴 댓글인지 확인)
        replyRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("댓글 삭제 완료");
    }

    private Reply getReply(Long id){
        return replyRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
    }

}