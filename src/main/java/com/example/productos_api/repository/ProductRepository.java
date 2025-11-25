package com.example.productos_api.repository;

import com.example.productos_api.config.TursoClient;
import com.example.productos_api.model.Product;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Repository
public class ProductRepository {

    @Autowired
    private TursoClient tursoClient;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public List<Product> findAll() {
        String sql = "SELECT * FROM products";
        JsonNode response = tursoClient.executeQuery(sql, null);
        return parseProducts(response);
    }

    public Optional<Product> findById(String id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        JsonNode response = tursoClient.executeQuery(sql, List.of(id));
        List<Product> products = parseProducts(response);
        return products.isEmpty() ? Optional.empty() : Optional.of(products.get(0));
    }

    public Product save(Product product) {
        if (product.getId() == null || product.getId().isEmpty()) {
            product.setId(UUID.randomUUID().toString());
        }

        String checkSql = "SELECT COUNT(*) as count FROM products WHERE id = ?";
        JsonNode checkResponse = tursoClient.executeQuery(checkSql, List.of(product.getId()));

        boolean exists = false;
        try {
            JsonNode rows = checkResponse.get(0).get("results").get("rows");
            if (rows.isArray() && rows.size() > 0) {
                exists = rows.get(0).get(0).asInt() > 0;
            }
        } catch (Exception e) {
            // Si hay error, asumimos que no existe
        }

        if (exists) {
            String sql = "UPDATE products SET name = ?, description = ?, price = ?, category = ?, image_url = ?, available = ?, is_specialty = ?, updated_at = ? WHERE id = ?";
            try {
                System.out.println("=== EXECUTING UPDATE ===");
                System.out.println("SQL: " + sql);
                System.out.println("Values:");
                System.out.println("  name: " + product.getName());
                System.out.println("  description: " + product.getDescription());
                System.out.println("  price: " + product.getPrice());
                System.out.println("  category: " + product.getCategory());
                System.out.println("  imageUrl: " + product.getImageUrl());
                System.out.println("  available: " + product.getAvailable());
                System.out.println("  isSpecialty: " + product.getIsSpecialty());
                System.out.println("  id: " + product.getId());
                
                tursoClient.executeQuery(sql, List.of(
                        product.getName(),
                        product.getDescription() != null ? product.getDescription() : "",
                        product.getPrice(),
                        product.getCategory(),
                        product.getImageUrl() != null ? product.getImageUrl() : "",
                        product.getAvailable() != null ? product.getAvailable() : 1,
                        product.getIsSpecialty() != null ? product.getIsSpecialty() : 0,
                        LocalDateTime.now().format(formatter),
                        product.getId()
                ));
                System.out.println("UPDATE ejecutado exitosamente");
            } catch (Exception e) {
                System.err.println("ERROR en UPDATE: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Error updating product: " + e.getMessage(), e);
            }
        } else {
            String sql = "INSERT INTO products (id, name, description, price, category, image_url, available, is_specialty, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            LocalDateTime now = LocalDateTime.now();
            tursoClient.executeQuery(sql, List.of(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getCategory(),
                    product.getImageUrl(),
                    product.getAvailable() != null ? product.getAvailable() : 1,
                    product.getIsSpecialty() != null ? product.getIsSpecialty() : 0,
                    now.format(formatter),
                    now.format(formatter)
            ));
        }

        return product;
    }

    public void deleteById(String id) {
        String sql = "DELETE FROM products WHERE id = ?";
        tursoClient.executeQuery(sql, List.of(id));
    }

    public boolean existsById(String id) {
        return findById(id).isPresent();
    }

    private List<Product> parseProducts(JsonNode response) {
        List<Product> products = new ArrayList<>();
        try {
            JsonNode results = response.get(0).get("results");
            JsonNode columns = results.get("columns");
            JsonNode rows = results.get("rows");

            if (rows.isArray()) {
                for (JsonNode row : rows) {
                    Product product = new Product();
                    for (int i = 0; i < columns.size(); i++) {
                        String columnName = columns.get(i).asText();
                        JsonNode value = row.get(i);

                        switch (columnName) {
                            case "id" -> product.setId(value.asText());
                            case "name" -> product.setName(value.asText());
                            case "description" -> product.setDescription(value.isNull() ? null : value.asText());
                            case "price" -> product.setPrice(value.asDouble());
                            case "category" -> product.setCategory(value.isNull() ? null : value.asText());
                            case "image_url" -> product.setImageUrl(value.isNull() ? null : value.asText());
                            case "available" -> product.setAvailable(value.isNull() ? null : value.asInt());
                            case "is_specialty" -> product.setIsSpecialty(value.isNull() ? null : value.asInt());
                            case "created_at" -> {
                                if (!value.isNull()) {
                                    String dateStr = value.asText();
                                    product.setCreatedAt(parseDateTime(dateStr));
                                }
                            }
                            case "updated_at" -> {
                                if (!value.isNull()) {
                                    String dateStr = value.asText();
                                    product.setUpdatedAt(parseDateTime(dateStr));
                                }
                            }
                        }
                    }
                    products.add(product);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error parsing products: " + e.getMessage(), e);
        }
        return products;
    }

    private LocalDateTime parseDateTime(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        try {
            // Intentar con formato ISO (con T): 2025-10-19T16:01:02.140655
            return LocalDateTime.parse(dateStr, isoFormatter);
        } catch (Exception e1) {
            try {
                // Intentar con formato con espacio: 2025-09-29 20:01:11
                return LocalDateTime.parse(dateStr, formatter);
            } catch (Exception e2) {
                // Si falla, intentar reemplazar espacio por T
                try {
                    String isoDate = dateStr.replace(" ", "T");
                    return LocalDateTime.parse(isoDate, isoFormatter);
                } catch (Exception e3) {
                    throw new RuntimeException("Cannot parse date: " + dateStr, e3);
                }
            }
        }
    }
}
