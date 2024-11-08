package org.ere.contest.cfservice.service;

import org.ere.contest.orderstarter.config.MetricsRegistry;
import org.ere.contest.orderstarter.model.Order;
import org.ere.contest.orderstarter.model.entity.OrderEntity;
import org.ere.contest.orderstarter.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@ComponentScan("org.ere.contest.orderstarter.config")
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final Cache orderCache;
    private final MetricsRegistry metricsRegistry;

    private final String cacheName = "orders";
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    public OrderServiceImpl(OrderRepository orderRepository, CacheManager cacheManager, MetricsRegistry metricsRegistry) {
        this.orderRepository = orderRepository;
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
