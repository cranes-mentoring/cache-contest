package com.ere.contest.hzctservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class HzctServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HzctServiceApplication.class, args);
    }

}
