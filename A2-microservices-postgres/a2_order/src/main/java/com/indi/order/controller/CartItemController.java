package com.indi.order.controller;


import com.indi.order.model.dto.CartItemRequest;
import com.indi.order.model.entity.CartItem;
import com.indi.order.model.service.CartItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {

    private final CartItemService cartItemService;

    public  CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public ResponseEntity<List<CartItem>>getCart(@RequestHeader (name = "X-User-ID") String userId) {
        return ResponseEntity.ok(this.cartItemService.getCart(userId));
    }

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader(name = "X-User-ID") String userId, @RequestBody CartItemRequest cartItemRequest) {
        if(!cartItemService.addToCart(userId, cartItemRequest)) {
            return ResponseEntity.badRequest().body("Product of of stock or user nof found or product not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/item/{productId}")
    public ResponseEntity<Void>removeFromCart(@RequestHeader(name = "X-User-ID") String userId, @PathVariable (name = "productId") String productId) {
        Boolean deleted = this.cartItemService.deleteIntemFromCart(userId, productId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }


}
