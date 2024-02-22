package com.example.productservice.controller;

import com.example.productservice.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/products")
public interface ProductController {

    @GetMapping("/all")
    ResponseEntity<List<Product>> getAllProducts();

    @GetMapping("/product/{id}")
    ResponseEntity<?> getProductById(@PathVariable Long id);

    @PostMapping("/add")
    ResponseEntity<Void> saveProduct(@RequestBody Product product);

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteProduct(@PathVariable Long id);

    @PatchMapping("/updateQuantity/{id}/{quantity}")
    public ResponseEntity<Void> updateProductQuantity(@PathVariable Long id, @PathVariable int quantity);

}
