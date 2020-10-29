package com.wdbyte.arthas;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ArthasApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ArthasApplication.class, args);
        Arthas.start();
    }

}
