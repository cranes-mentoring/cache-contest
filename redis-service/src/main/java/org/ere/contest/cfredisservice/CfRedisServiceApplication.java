package org.ere.contest.cfredisservice;

import org.ere.contest.orderstarter.config.MetricsRegistry;
import org.ere.contest.orderstarter.web.GlobalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

@EnableCaching
@SpringBootApplication
@Import({MetricsRegistry.class, GlobalExceptionHandler.class})
public class CfRedisServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CfRedisServiceApplication.class, args);
    }

}
