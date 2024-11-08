package org.ere.contest.cfkafkaservice.service;

import org.ere.contest.orderstarter.config.MetricsRegistry;
import org.ere.contest.orderstarter.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class CacheService {

    private static final Logger logger = LoggerFactory.getLogger(CacheService.class);

    private final String cacheName = "orders";
    private final Cache orderCache;
    private final MetricsRegistry metricsRegistry;

    public CacheService(CacheManager cacheManager, MetricsRegistry metricsRegistry) {
        this.metricsRegistry = metricsRegistry;

        this.orderCache = cacheManager.getCache(cacheName);
    }

    public void evict(String orderId) {
        var evicted = orderCache.evictIfPresent(orderId);
        if (evicted) {
            logger.info("Evicted order id {}", orderId);
        }
    }

    public Optional<Order> getOrder(String orderId) {
        var cachedOrder = orderCache.get(orderId, Order.class);
        if (cachedOrder == null) {
            metricsRegistry.incrementCacheMissCounter();
            return Optional.empty();
        }

        metricsRegistry.incrementCacheHitCounter();
        return Optional.of(cachedOrder);
    }

    public void putOrder(Order order) {
        orderCache.put(order.uuid(), order);
        logger.info("Added order id {}", order.uuid());
    }
}
