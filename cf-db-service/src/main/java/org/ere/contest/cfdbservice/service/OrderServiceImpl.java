package org.ere.contest.cfdbservice.service;

import org.ere.contest.cfdbservice.model.entity.CacheEntity;
import org.ere.contest.orderstarter.config.MetricsRegistry;
import org.ere.contest.orderstarter.model.Order;
import org.ere.contest.orderstarter.model.entity.OrderEntity;
import org.ere.contest.orderstarter.repository.OrderRepository;
import org.ere.contest.cfdbservice.repository.CacheRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CacheRepository cacheRepository;
    private final MetricsRegistry metricsRegistry;
    private final Cache orderCache;

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final String cacheName = "orders";

    public OrderServiceImpl(OrderRepository orderRepository, CacheManager cacheManager, CacheRepository cacheRepository, MetricsRegistry metricsRegistry) {
        this.orderRepository = orderRepository;
        this.cacheRepository = cacheRepository;
        this.metricsRegistry = metricsRegistry;

        orderCache = cacheManager.getCache(cacheName);
    }

    @Override
    @Transactional
    public String createOrder(Order order) {
        var saved = orderRepository.saveAndFlush(map(order));
        return saved.getUuid().toString();
    }

    @Override
    @Transactional
    public void deleteOrder(String orderId) {
        orderRepository.deleteById(UUID.fromString(orderId));

        orderCache.evictIfPresent(orderId);
        var cachedEntity = cacheRepository.findById(cacheName);
        cachedEntity.ifPresent(this::updateCache);

        logger.info("Order with id {} has been deleted", orderId);
    }

    @Override
    public Optional<Order> getOrder(String orderId) {
        var cachedOrder = orderCache.get(orderId, Order.class);
        if (cachedOrder != null) {
            logger.info("Order with id {} has been loaded from cache", orderId);
            metricsRegistry.incrementCacheHitCounter();

            return Optional.of(cachedOrder);
        }

        var entity = orderRepository.findById(UUID.fromString(orderId));

        if (entity.isPresent()) {
            var order = map(entity.get());
            orderCache.put(order.uuid(), order);

            logger.info("Order with id {} has been added to cache", orderId);

            return Optional.of(order);
        }

        metricsRegistry.incrementCacheMissCounter();

        return Optional.empty();
    }

    private void updateCache(CacheEntity cachedEntity) {
        cachedEntity.setLastUpdate(Instant.now());
        cacheRepository.save(cachedEntity);
        logger.info("cache updated {}", cacheName);
    }

    private OrderEntity map(Order order) {
        var uuid = order.uuid() == null ? UUID.randomUUID() : UUID.fromString(order.uuid());
        return new OrderEntity(
                uuid,
                order.username(),
                order.timestamp()
        );
    }

    private Order map(OrderEntity entity) {
        return new Order(
                entity.getUuid().toString(),
                entity.getUsername(),
                entity.getTimestamp()
        );
    }
}
