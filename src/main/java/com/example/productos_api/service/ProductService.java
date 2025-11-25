package com.example.productos_api.service;

import com.example.productos_api.model.Product;
import com.example.productos_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(String id, Product productDetails) {
        try {
            System.out.println("=== UPDATE PRODUCT ===");
            System.out.println("ID: " + id);
            System.out.println("Name: " + productDetails.getName());
            System.out.println("Price: " + productDetails.getPrice());
            System.out.println("Available: " + productDetails.getAvailable());
            System.out.println("IsSpecialty: " + productDetails.getIsSpecialty());
            
            Optional<Product> existingProduct = productRepository.findById(id);
            if (existingProduct.isPresent()) {
                Product product = existingProduct.get();
                
                // Solo actualizar campos que no sean null
                if (productDetails.getName() != null) {
                    product.setName(productDetails.getName());
                }
                if (productDetails.getDescription() != null) {
                    product.setDescription(productDetails.getDescription());
                }
                if (productDetails.getPrice() != null) {
                    product.setPrice(productDetails.getPrice());
                }
                if (productDetails.getCategory() != null) {
                    product.setCategory(productDetails.getCategory());
                }
                if (productDetails.getImageUrl() != null) {
                    product.setImageUrl(productDetails.getImageUrl());
                }
                
                // Validar y establecer valores por defecto si son null
                product.setAvailable(productDetails.getAvailable() != null ? productDetails.getAvailable() : product.getAvailable());
                product.setIsSpecialty(productDetails.getIsSpecialty() != null ? productDetails.getIsSpecialty() : product.getIsSpecialty());
                
                System.out.println("Guardando producto actualizado...");
                Product saved = productRepository.save(product);
                System.out.println("Producto guardado exitosamente");
                return saved;
            }
            System.out.println("Producto no encontrado");
            return null;
        } catch (Exception e) {
            System.err.println("ERROR en updateProduct: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error updating product: " + e.getMessage(), e);
        }
    }

    public boolean deleteProduct(String id) {
        System.out.println("=== DELETE PRODUCT ===");
        System.out.println("ID recibido: " + id);
        System.out.println("Longitud del ID: " + id.length());
        
        boolean exists = productRepository.existsById(id);
        System.out.println("¿Existe el producto? " + exists);
        
        if (exists) {
            productRepository.deleteById(id);
            System.out.println("Producto eliminado exitosamente");
            return true;
        }
        
        System.out.println("Producto NO encontrado - listando todos los productos:");
        List<Product> allProducts = productRepository.findAll();
        for (Product p : allProducts) {
            System.out.println("  - ID: " + p.getId() + " | Nombre: " + p.getName());
        }
        
        return false;
    }
}
