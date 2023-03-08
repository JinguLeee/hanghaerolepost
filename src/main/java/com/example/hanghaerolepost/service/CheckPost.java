package com.example.hanghaerolepost.service;

import com.example.hanghaerolepost.entity.Post;
import com.example.hanghaerolepost.entity.User;
import com.example.hanghaerolepost.entity.UserRoleEnum;
import com.example.hanghaerolepost.repository.PostRepository;
import com.example.hanghaerolepost.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CheckPost {
    private final PostRepository postRepository;

    private final ReplyRepository replyRepository;
    public Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
    }
    public void getPostRole(Long id, User user) {
        if (user.getRole() == UserRoleEnum.ADMIN) return;
        postRepository.findByIdAndUser(id, user).orElseThrow(
                () -> new IllegalArgumentException("권한이 없습니다.")
        );
    }
    public void getReplyRole(Long id, User user) {
        if (user.getRole() == UserRoleEnum.ADMIN) return;
        replyRepository.findByIdAndUser(id, user).orElseThrow(
                () -> new IllegalArgumentException("권한이 없습니다.")
        );
    }
}
