package org.ere.contest.orderstarter.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsRegistry {

    private final Counter cacheMissCounter;
    private final Counter cacheHitCounter;

    public MetricsRegistry(MeterRegistry meterRegistry, @Value("${spring.application.name}") String appName) {

        this.cacheMissCounter = meterRegistry.counter("cache.misses", "cache", "orders", "app", appName);
        this.cacheHitCounter = meterRegistry.counter("cache.hits", "cache", "orders", "app", appName);
    }

    public void incrementCacheMissCounter() {
        cacheMissCounter.increment();
    }

    public void incrementCacheHitCounter() {
        cacheHitCounter.increment();
    }
}
