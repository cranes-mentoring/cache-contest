package org.ere.contest.cfdbservice.config;

import jakarta.annotation.PostConstruct;
import org.ere.contest.cfdbservice.repository.CacheRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@EnableScheduling
@Configuration
public class CacheControl {

    private final CacheManager cacheManager;
    private final CacheRepository cacheRepository;

    private final ConcurrentHashMap<String, Instant> cacheMap = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(CacheControl.class);

    public CacheControl(CacheManager cacheManager, CacheRepository cacheRepository) {
        this.cacheManager = cacheManager;
        this.cacheRepository = cacheRepository;
    }

    @PostConstruct
    public void init() {
        cacheRepository.findAll()
                .forEach(c -> cacheMap.put(c.getName(), c.getLastUpdate()));
    }

    @Scheduled(fixedRate = 60000)
    public void resetCache() {
        logger.info("start reset cache");

        cacheRepository.findAll().forEach(c -> {
            var name = c.getName();

            if (cacheMap.get(name).isBefore(c.getLastUpdate())) {
                var cache = cacheManager.getCache(name);
                if (cache != null) {
                    cache.clear();
                } else {
                    logger.error("Could not find cache for {}", name);
                }
            }
        });
    }

}
