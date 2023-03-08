package com.example.hanghaerolepost.repository;

import com.example.hanghaerolepost.entity.Post;
import com.example.hanghaerolepost.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();

    Optional<Post> findByIdAndUser(Long id, User user);
}
