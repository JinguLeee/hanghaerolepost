package com.example.hanghaerolepost.service;

import com.example.hanghaerolepost.dto.ReplyRequestDto;
import com.example.hanghaerolepost.dto.ReplyResponseDto;
import com.example.hanghaerolepost.entity.Post;
import com.example.hanghaerolepost.entity.Reply;
import com.example.hanghaerolepost.entity.User;
import com.example.hanghaerolepost.entity.UserRoleEnum;
import com.example.hanghaerolepost.jwt.JwtUtil;
import com.example.hanghaerolepost.repository.PostRepository;
import com.example.hanghaerolepost.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public ReplyResponseDto createReply(Long id, ReplyRequestDto requestDto, HttpServletRequest request) {
        User user = jwtUtil.getUser(request); // 토큰이 있는 경우 사용자의 정보를 받아온다.
        Post post = getPost(id);  // 게시글이 존재하는지 확인 후 가져온다
        Reply reply = replyRepository.saveAndFlush(new Reply(requestDto.getReply(), user, post));
        return new ReplyResponseDto(reply, user.getUsername());
    }

    @Transactional
    public ReplyResponseDto update(Long id, ReplyRequestDto replyRequestDto, HttpServletRequest request) {
        User user = jwtUtil.getUser(request); // 토큰이 있는 경우 사용자의 정보를 받아온다.
        Reply reply = getReply(id); // 댓글이 존재하는지 확인 후 가져온다.
        checkReplyRole(id, user);  // 권한을 확인한다 (자신이 쓴 댓글인지 확인)
        reply.update(replyRequestDto);
        return new ReplyResponseDto(reply, user.getUsername());
    }

    @Transactional
    public ResponseEntity<String> delete(Long id, HttpServletRequest request) {
        User user = jwtUtil.getUser(request);      // 토큰이 있는 경우 사용자의 정보를 받아온다.
        getReply(id); // 댓글이 존재하는지 확인 후 가져온다.
        checkReplyRole(id, user);  // 권한을 확인한다 (자신이 쓴 댓글인지 확인)
        replyRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("댓글 삭제 완료");
    }

    private Reply getReply(Long id){
        return replyRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
    }

    private Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
    }
    private void checkReplyRole(Long id, User user) {
        if (user.getRole() == UserRoleEnum.ADMIN) return;
        replyRepository.findByIdAndUser(id, user).orElseThrow(
                () -> new IllegalArgumentException("권한이 없습니다.")
        );
    }
}