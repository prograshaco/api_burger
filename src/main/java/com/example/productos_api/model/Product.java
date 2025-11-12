package com.example.productos_api.model;

import java.time.LocalDateTime;

public class Product {

    private String id;
    private String name;
    private String description;
    private Double price;
    private String category;
    private String imageUrl;
    private Integer available;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer isSpecialty;

    public Product() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Integer getAvailable() { return available; }
    public void setAvailable(Integer available) { this.available = available; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Integer getIsSpecialty() { return isSpecialty; }
    public void setIsSpecialty(Integer isSpecialty) { this.isSpecialty = isSpecialty; }
}
