package com.example.hanghaerolepost.controller;

import com.example.hanghaerolepost.dto.PostRequestDto;
import com.example.hanghaerolepost.dto.PostResponseDto;
import com.example.hanghaerolepost.service.PostService;
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

    @PostMapping()
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto, HttpServletRequest request) {
        return postService.createPost(postRequestDto, request);
    }

    @GetMapping()
    public List<PostResponseDto> getPosts() {
        return postService.getPost();
    }

    @GetMapping("/{id}")
    public PostResponseDto getDetail(@PathVariable Long id) {
        return postService.getOnePost(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost (@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, HttpServletRequest request) {
        return postService.update(id, postRequestDto, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id, HttpServletRequest request) {
        return postService.delete(id, request);
    }
}
