package org.ere.contest.cfkafkaservice.service;

import org.ere.contest.orderstarter.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class CacheService {

    private final String cacheName = "orders";
    private static final Logger logger = LoggerFactory.getLogger(CacheService.class);

    private final CacheManager cacheManager;

    public CacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void evict(String orderId) {
        var evicted = Objects.requireNonNull(cacheManager.getCache(cacheName)).evictIfPresent(orderId);
        if (evicted) {
            logger.info("Evicted order id {}", orderId);
        }
    }

    public Optional<Order> getOrder(String orderId) {
        var cache = cacheManager.getCache(cacheName);
        assert cache != null;

        var cachedOrder = cache.get(orderId, Order.class);
        return Optional.ofNullable(cachedOrder);
    }

    public void putOrder(Order order) {
        Objects.requireNonNull(cacheManager.getCache(cacheName)).put(order.uuid(), order);
        logger.info("Added order id {}", order.uuid());
    }
}
