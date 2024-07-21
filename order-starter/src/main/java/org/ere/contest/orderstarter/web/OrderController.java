package org.ere.contest.orderstarter.web;

import org.ere.contest.orderstarter.model.Order;
import org.ere.contest.orderstarter.model.request.OrderRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface OrderController {

    ResponseEntity<String> createOrder(@RequestBody OrderRequest order);

    ResponseEntity<String> deleteOrder(@PathVariable String orderUuid);

    ResponseEntity<Order> getOrder(@PathVariable String orderUuid);

    default Order map(OrderRequest request) {
        return new Order(
                request.uuid(),
                request.username(),
                request.timestamp()
        );
    }
}
