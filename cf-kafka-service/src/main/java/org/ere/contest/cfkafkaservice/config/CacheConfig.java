package org.ere.contest.cfkafkaservice.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.ere.contest.cfkafkaservice.config.kafka.KafkaRemovalListener;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(KafkaTemplate<String, String> kafkaTemplate) {
        var manager = new CaffeineCacheManager();
        manager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(500)
                .expireAfterAccess(600, TimeUnit.SECONDS)
                .removalListener(new KafkaRemovalListener(kafkaTemplate)));

        return manager;
    }
}
