package com.example.hanghaerolepost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing  // TimeStamped을 사용할때 필요한 어노테이션
@SpringBootApplication
public class HanghaerolepostApplication {

    public static void main(String[] args) {
        SpringApplication.run(HanghaerolepostApplication.class, args);
    }

}
