package org.ere.contest.cfkafkaservice.service;

import org.ere.contest.orderstarter.model.Order;
import org.ere.contest.orderstarter.model.entity.OrderEntity;
import org.ere.contest.orderstarter.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CacheService cacheService;
    private final TransactionTemplate transactionTemplate;

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    public OrderServiceImpl(
            OrderRepository orderRepository,
            CacheService cacheService,
            TransactionTemplate transactionTemplate
    ) {
        this.orderRepository = orderRepository;
        this.cacheService = cacheService;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public String createOrder(Order order) {
        var saved = transactionTemplate.execute(_ -> orderRepository.saveAndFlush(map(order)));
        return Objects.requireNonNull(saved).getUuid().toString();
    }

    @Override
    public void deleteOrder(String orderId) {
        var orderUuid = UUID.fromString(orderId);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                orderRepository.deleteById(orderUuid);
            }
        });

        cacheService.evict(orderId);
        logger.info("Order with id {} has been deleted", orderId);
    }

    @Override
    public Optional<Order> getOrder(String orderId) {
        var cachedOrder = cacheService.getOrder(orderId);

        if (cachedOrder.isPresent()) {
            logger.info("Order with id {} has been loaded from cache", orderId);
            return cachedOrder;
        }

        var entity = orderRepository.findById(UUID.fromString(orderId));

        if (entity.isPresent()) {
            var order = map(entity.get());
            cacheService.putOrder(order);
            logger.info("Order with id {} has been added to cache", orderId);

            return Optional.of(order);
        }

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
