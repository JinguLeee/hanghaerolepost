package com.example.hanghaerolepost.entity;

import com.example.hanghaerolepost.dto.ReplyRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Reply extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String reply;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post post;

    public Reply(String reply, User user, Post post) {
        this.reply = reply;
        this.user = user;
        this.post = post;
    }

    @Transactional
    public void update(ReplyRequestDto replyRequestDto) {
        this.reply = replyRequestDto.getReply();
    }
}
