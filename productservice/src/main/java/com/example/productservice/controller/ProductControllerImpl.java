package com.example.productservice.controller;


import com.example.productservice.model.Product;
import com.example.productservice.service.ProductService;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class ProductControllerImpl implements ProductController{

    private final ProductService productService;

    private final MeterRegistry meterRegistry;

    @Autowired
    public ProductControllerImpl(ProductService productService, MeterRegistry meterRegistry) {
        this.productService = productService;
        this.meterRegistry = meterRegistry;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products = productService.getAll();
            meterRegistry.counter("requests.to.show.product.list").increment();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            Optional<Product> optionalProduct = productService.getById(id);
            return ResponseEntity.ok(optionalProduct.orElseThrow(() -> new NoSuchElementException("Product not found")));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Void> saveProduct(@RequestBody Product product) {
        try {
            productService.save(product);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/updateQuantity/{id}/{quantity}")
    public ResponseEntity<Void> updateProductQuantity(@PathVariable Long id, @PathVariable int quantity) {
        try {
            productService.updateProductQuantity(id, quantity);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            Optional<Product> optionalProduct = productService.getById(id);

            if (optionalProduct.isPresent()) {
                Product productToDelete = optionalProduct.get();
                productService.delete(productToDelete);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
