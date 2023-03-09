package com.example.hanghaerolepost.controller;

import com.example.hanghaerolepost.dto.PostRequestDto;
import com.example.hanghaerolepost.dto.PostResponseDto;
import com.example.hanghaerolepost.service.PostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Swagger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    @ApiOperation(value = "게시글 등록", notes = "게시글을 등록한다.")
    @PostMapping()
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto, HttpServletRequest request) {
        return postService.createPost(postRequestDto, request);
    }

    @ApiOperation(value = "게시글 조회", notes = "전체 게시글을 조회한다.")
    @GetMapping()
    public List<PostResponseDto> getPosts() {
        return postService.getPost();
    }

    @ApiOperation(value = "게시글 조회", notes = "하나의 게시글을 조회한다.")
    @GetMapping("/{postId}")
    public PostResponseDto getDetail(@PathVariable Long postId) {
        return postService.getOnePost(postId);
    }

    @ApiOperation(value = "게시글 수정", notes = "자신이 쓴 게시글 중 선택한 게시글을 수정한다.")
    @PutMapping("/{postId}")
    public PostResponseDto updatePost (@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto, HttpServletRequest request) {
        return postService.update(postId, postRequestDto, request);
    }

    @ApiOperation(value = "게시글 삭제", notes = "자신이 쓴 게시글 중 선택한 게시글을 삭제한다.")
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, HttpServletRequest request) {
        return postService.delete(postId, request);
    }
}
