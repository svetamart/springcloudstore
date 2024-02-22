package com.example.orderservice.service;

import com.example.orderservice.model.CartProduct;
import com.example.orderservice.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {

    private final ProductServiceRestClient productService;

    private ShoppingCart shoppingCart;

    @Autowired
    public ShoppingCartService(ProductServiceRestClient productService) {
        this.productService = productService;
        this.shoppingCart = new ShoppingCart();
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void addToCart(Long productId, int quantity) {
        try{
            CartProduct product = productService.getProductById(productId);
            int availableQuantity = product.getQuantity();
            if (quantity <= availableQuantity) {
                product.setQuantity(quantity);
                shoppingCart.addProduct(product);
            } else {
                throw new IllegalArgumentException("Not enough stock available");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while processing addToCart", e);
        }
    }

    public void removeFromCart(Long productId) {
        shoppingCart.removeProduct(productId);
    }

    public void clearCart() {
        shoppingCart.clearCart();
    }

    public String order() {
        try {
            List<CartProduct> products = shoppingCart.getProducts();

            if (products.isEmpty()) {
                return "Your shopping cart is empty.";
            }

            StringBuilder orderDetails = new StringBuilder("Order Details:\n");

            double totalCost = products.stream()
                    .mapToDouble(product -> {
                        orderDetails.append(String.format("%s - $%.2f x %d = %.2f RUB\n",
                                product.getName(), product.getPrice(), product.getQuantity(),
                                product.getPrice() * product.getQuantity()));
                        return product.getPrice() * product.getQuantity();
                    })
                    .sum();

            orderDetails.append(String.format("Total Cost: %.2f RUB", totalCost));

            for (CartProduct cartProduct : products) {
                Long productId = cartProduct.getId();
                int quantityInCart = cartProduct.getQuantity();

                CartProduct productInStock = productService.getProductById(productId);
                int quantityInStock = productInStock.getQuantity();

                productService.updateProductQuantity(productId, quantityInStock - quantityInCart);
            }
            shoppingCart.clearCart();

            return orderDetails.toString();
        } catch (RuntimeException e) {
            throw new RuntimeException("Error while processing the order", e);
        }
    }
}
