package com.example.hanghaerolepost.service;

import com.example.hanghaerolepost.dto.PostRequestDto;
import com.example.hanghaerolepost.dto.PostResponseDto;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, HttpServletRequest request) {
        User user = jwtUtil.getUser(request); // 토큰이 있는 경우 사용자의 정보를 받아온다.
        Post post = postRepository.saveAndFlush(new Post(requestDto, user));
        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPost() {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();

        for (Post post : postList) {
            List<ReplyResponseDto> replyResponseList = getReplyResponseList(post);
            postResponseDtoList.add(new PostResponseDto(post, replyResponseList));
        }

        return postResponseDtoList;
    }

    @Transactional
    public PostResponseDto getOnePost(Long postId) {
        Post post = getPost(postId);    // 게시글이 존재하는지 확인 후 가져온다
        return new PostResponseDto(post, getReplyResponseList(post));
    }

    @Transactional
    public PostResponseDto update(Long postId, PostRequestDto postRequestDto, HttpServletRequest request) {
        User user = jwtUtil.getUser(request);   // 토큰이 있는 경우 사용자의 정보를 받아온다.

        Post post = getPost(postId);    // 게시글이 존재하는지 확인 후 가져온다

        checkPostRole(postId, user);  // 권한을 확인한다 (자신이 쓴 글인지 확인)

        post.update(postRequestDto);
        return new PostResponseDto(post, getReplyResponseList(post));
    }

    @Transactional
    public ResponseEntity<String> delete(Long PostId, HttpServletRequest request) {
        User user = jwtUtil.getUser(request);      // 토큰이 있는 경우 사용자의 정보를 받아온다.

        getPost(PostId);        // 게시글이 존재하는지 확인 후 가져온다

        checkPostRole(PostId, user);  // 권한을 확인한다 (자신이 쓴 글인지 확인)

        replyRepository.deleteByPostId(PostId); // 댓글 먼저 삭제
        postRepository.deleteById(PostId);
        return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");
    }

    private Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
    }

    private void checkPostRole(Long id, User user) {
        if (user.getRole() == UserRoleEnum.ADMIN) return;
        postRepository.findByIdAndUser(id, user).orElseThrow(
                () -> new IllegalArgumentException("권한이 없습니다.")
        );
    }

    private  List<ReplyResponseDto> getReplyResponseList (Post post) {
        List<ReplyResponseDto> replyResponseList = new ArrayList<>();
        for (Reply reply : post.getReplyList()) {
            replyResponseList.add(new ReplyResponseDto(reply));
        }
        return replyResponseList;
    }
}