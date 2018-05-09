package com.mikelduke.charlie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableAsync
public class CharlieApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(CharlieApplication.class, args);
    }
}
