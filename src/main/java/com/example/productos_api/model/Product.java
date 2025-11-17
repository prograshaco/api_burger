package com.example.productos_api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Modelo de producto del restaurante")
public class Product {

    @Schema(description = "ID único del producto", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;
    
    @Schema(description = "Nombre del producto", example = "Hamburguesa Clásica", required = true)
    private String name;
    
    @Schema(description = "Descripción detallada del producto", example = "Deliciosa hamburguesa con carne de res, lechuga, tomate y queso")
    private String description;
    
    @Schema(description = "Precio del producto", example = "12.99", required = true)
    private Double price;
    
    @Schema(description = "Categoría del producto", example = "Hamburguesas", required = true)
    private String category;
    
    @Schema(description = "URL de la imagen del producto", example = "https://example.com/burger.jpg")
    private String imageUrl;
    
    @Schema(description = "Disponibilidad del producto (1=disponible, 0=no disponible)", example = "1")
    private Integer available;
    
    @Schema(description = "Fecha de creación del producto")
    private LocalDateTime createdAt;
    
    @Schema(description = "Fecha de última actualización del producto")
    private LocalDateTime updatedAt;
    
    @Schema(description = "Indica si es una especialidad (1=sí, 0=no)", example = "0")
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
