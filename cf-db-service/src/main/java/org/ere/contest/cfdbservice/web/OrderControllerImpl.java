package org.ere.contest.cfdbservice.web;

import org.ere.contest.orderstarter.model.Order;
import org.ere.contest.orderstarter.model.request.OrderRequest;
import org.ere.contest.orderstarter.web.OrderController;
import org.ere.contest.cfdbservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/order")
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    public OrderControllerImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest order) {
        var uuid = orderService.createOrder(map(order));
        return ResponseEntity.status(HttpStatus.CREATED).body(uuid);
    }

    @GetMapping("/delete/{orderUuid}")
    public ResponseEntity<String> deleteOrder(@PathVariable String orderUuid) {
        orderService.deleteOrder(orderUuid);
        return ResponseEntity.ok().body("deleted");
    }

    @GetMapping("/{orderUuid}")
    public ResponseEntity<Order> getOrder(@PathVariable String orderUuid) {
        return orderService.getOrder(orderUuid)
                .map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
