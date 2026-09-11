package com.ram.ecommerce.order;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService service;
    public OrderController(OrderService service){this.service=service;}
    @PostMapping("/{userId}") public Order create(@PathVariable String userId,@RequestParam String idempotencyKey){return service.create(userId,idempotencyKey);}
    @GetMapping("/{id}") public Order get(@PathVariable Long id){return service.get(id);}
    @PatchMapping("/{id}/status") public Order status(@PathVariable Long id,@RequestParam OrderStatus value){return service.transition(id,value);}
}
