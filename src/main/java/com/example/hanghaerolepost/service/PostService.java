package com.example.hanghaerolepost.service;

import com.example.hanghaerolepost.dto.PostRequestDto;
import com.example.hanghaerolepost.dto.PostResponseDto;
import com.example.hanghaerolepost.entity.Post;
import com.example.hanghaerolepost.entity.User;
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
    private final CheckPost checkPost;
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
            postResponseDtoList.add(new PostResponseDto(post));
        }

        return postResponseDtoList;
    }

    @Transactional
    public PostResponseDto getOnePost(Long id) {
        Post post = checkPost.getPost(id);    // 게시글이 존재하는지 확인 후 가져온다

        return new PostResponseDto(post);
    }

    @Transactional
    public ResponseEntity<String> update(Long id, PostRequestDto postRequestDto, HttpServletRequest request) {
        User user = jwtUtil.getUser(request);   // 토큰이 있는 경우 사용자의 정보를 받아온다.

        Post post = checkPost.getPost(id);    // 게시글이 존재하는지 확인 후 가져온다

        checkPost.getPostRole(id, user);  // 권한을 확인한다 (자신이 쓴 글인지 확인)

        post.update(postRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("수정 완료");
    }

    @Transactional
    public ResponseEntity<String> delete(Long id, HttpServletRequest request) {
        User user = jwtUtil.getUser(request);      // 토큰이 있는 경우 사용자의 정보를 받아온다.

        checkPost.getPost(id);        // 게시글이 존재하는지 확인 후 가져온다

        checkPost.getPostRole(id, user);  // 권한을 확인한다 (자신이 쓴 글인지 확인)

        replyRepository.deleteByPostId(id); // 댓글 먼저 삭제
        postRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");
    }
}