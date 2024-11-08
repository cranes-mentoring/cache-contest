package org.ere.contest.cfredisservice.service;

import org.ere.contest.orderstarter.config.MetricsRegistry;
import org.ere.contest.orderstarter.model.Order;
import org.ere.contest.orderstarter.model.entity.OrderEntity;
import org.ere.contest.orderstarter.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MetricsRegistry metricsRegistry;
    private final Cache orderCache;
    private final String cacheName = "orders";

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    public OrderServiceImpl(OrderRepository orderRepository, MetricsRegistry metricsRegistry, CacheManager cacheManager) {
        this.orderRepository = orderRepository;
        this.metricsRegistry = metricsRegistry;

        this.orderCache = cacheManager.getCache(cacheName);
    }

    @Override
    @Transactional
    public String createOrder(Order order) {
        var saved = orderRepository.saveAndFlush(map(order));
        return Objects.requireNonNull(saved).getUuid().toString();
    }

    @Override
    @Transactional
    public void deleteOrder(String orderId) {
        var orderUuid = UUID.fromString(orderId);
        orderRepository.deleteById(orderUuid);

        orderCache.evict(orderUuid);

        logger.info("Order with id {} has been deleted", orderId);
    }

    @Override
    public Optional<Order> getOrder(String orderId) {
        var val = orderCache.get(orderId, Order.class);

        if (Objects.isNull(val)) {
            metricsRegistry.incrementCacheMissCounter();

            return orderRepository
                    .findById(UUID.fromString(orderId))
                    .map(o -> {
                        var mapped = map(o);
                        orderCache.put(orderId, mapped);

                        return mapped;
                    });
        } else {
            metricsRegistry.incrementCacheHitCounter();
            return Optional.of(val);
        }
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
