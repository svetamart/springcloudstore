package com.example.reviewservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class ProductServiceRestClient {

    private final RestTemplate restTemplate;

    @Autowired
    public ProductServiceRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getProductTitleById(Long productId) {
        String url = "http://localhost:8081/api/products/product/" + productId;

        Map<String, Object> jsonResponse = restTemplate.getForObject(url, Map.class);
        if (jsonResponse != null && jsonResponse.containsKey("name")) {
            return jsonResponse.get("name").toString();
        } else {
            return "Unknown Product";
        }
    }
}
