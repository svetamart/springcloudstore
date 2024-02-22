package com.example.orderservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartProduct {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String category;
    private int quantity;

    public CartProduct() {

    }

    public CartProduct(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("price") double price,
            @JsonProperty("category") String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.quantity = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
