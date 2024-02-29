package com.example.orderservice.controller;

import com.example.orderservice.model.ShoppingCart;
import com.example.orderservice.service.ShoppingCartService;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shoppingCart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/contents")
    public ResponseEntity<ShoppingCart> getShoppingCartContents() {
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart();
        return ResponseEntity.ok(shoppingCart);
    }

    @PatchMapping("/addProduct/{productId}/{quantity}")
    public ResponseEntity<String> addToCart(@PathVariable Long productId, @PathVariable int quantity) {
        shoppingCartService.addToCart(productId, quantity);
        return ResponseEntity.ok("Product added to the cart successfully");
    }

    @PostMapping("/removeProduct/{productId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long productId) {
        try {
            shoppingCartService.removeFromCart(productId);
            return ResponseEntity.ok("Product removed from the cart successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while processing removeFromCart");
        }
    }

    @PostMapping("/clearCart")
    public ResponseEntity<String> clearCart() {
        try {
            shoppingCartService.clearCart();
            return ResponseEntity.ok("Shopping cart cleared successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while processing clearCart");
        }
    }

    @PostMapping("/order")
    public ResponseEntity<String> order() {
        try {
            String orderDetails = shoppingCartService.order();
            return ResponseEntity.ok(orderDetails);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while processing the order");
        }
    }
}
