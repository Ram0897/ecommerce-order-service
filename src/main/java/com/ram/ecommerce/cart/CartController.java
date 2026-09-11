package com.ram.ecommerce.cart;

import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
 private final CartService service;
 public CartController(CartService service){this.service=service;}
 @PostMapping("/{userId}/items") public Cart add(@PathVariable String userId,@RequestParam UUID productId,@RequestParam @Min(1) int quantity){return service.addItem(userId,productId,quantity);}
 @GetMapping("/{userId}") public Cart get(@PathVariable String userId){return service.get(userId);}
 @DeleteMapping("/{userId}") public ResponseEntity<Void> clear(@PathVariable String userId){service.clear(service.get(userId));return ResponseEntity.noContent().build();}
}
