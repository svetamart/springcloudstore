package com.example.orderservice.model;
import java.util.List;
import java.util.ArrayList;

public class ShoppingCart {

    private final List<CartProduct> products;

    public ShoppingCart() {
        this.products = new ArrayList<>();
    }

    public List<CartProduct> getProducts() {
        return products;
    }

    public void addProduct(CartProduct product) {
        products.add(product);
    }

    public void removeProduct(Long productId) {
        products.removeIf(cartProduct -> cartProduct.getId().equals(productId));
    }

    public void clearCart() {
        products.clear();
    }
}
