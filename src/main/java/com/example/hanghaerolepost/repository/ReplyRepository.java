package com.example.hanghaerolepost.repository;

import com.example.hanghaerolepost.entity.Reply;
import com.example.hanghaerolepost.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Optional<Reply> findByIdAndUser(Long id, User user);
    void deleteByPostId(Long id);
}
