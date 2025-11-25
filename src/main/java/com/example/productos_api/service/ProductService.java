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
        System.out.println("=== UPDATE PRODUCT ===");
        System.out.println("ID: " + id);
        System.out.println("Name: " + productDetails.getName());
        System.out.println("Price: " + productDetails.getPrice());
        System.out.println("Available: " + productDetails.getAvailable());
        System.out.println("IsSpecialty: " + productDetails.getIsSpecialty());
        
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setCategory(productDetails.getCategory());
            product.setImageUrl(productDetails.getImageUrl());
            
            // Validar y establecer valores por defecto si son null
            product.setAvailable(productDetails.getAvailable() != null ? productDetails.getAvailable() : 1);
            product.setIsSpecialty(productDetails.getIsSpecialty() != null ? productDetails.getIsSpecialty() : 0);
            
            System.out.println("Guardando producto actualizado...");
            Product saved = productRepository.save(product);
            System.out.println("Producto guardado exitosamente");
            return saved;
        }
        System.out.println("Producto no encontrado");
        return null;
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
