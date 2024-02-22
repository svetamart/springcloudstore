package com.example.orderservice.service;


import com.example.orderservice.model.CartProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class ProductServiceRestClient {

    private final RestTemplate restTemplate;

    @Autowired
    public ProductServiceRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CartProduct getProductById(Long id) {
        String url = "http://localhost:8081/api/products/product/" + id;
        ResponseEntity<CartProduct> response = restTemplate.getForEntity(url, CartProduct.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Error while fetching product: " + response.getStatusCode());
        }
    }


    public void updateProductQuantity(Long id, int quantity) {
        String url = "http://localhost:8081/api/products/updateQuantity/" + id + "/" + quantity;
        restTemplate.patchForObject(url, null, Void.class);
    }
}
