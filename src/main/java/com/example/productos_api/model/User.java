package com.example.productos_api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "users")
@Schema(description = "Modelo de usuario")
public class User {

    @Id
    @Schema(description = "Id del usuario", example = "user_8e481280-550e-41d4-a716-446655440000")
    private String id;

    @Column(name = "username")
    @Schema(description = "Nombre del usuario", example = "juanperez")
    private String username;

    @Column(name = "email")
    @Schema(description = "Correo del usuario", example = "juan.perez@example.com")
    private String email;

    @Column(name = "password")
    @Schema(description = "Password del usuario", example = "password123")
    private String password;

    @Column(name = "name")
    @Schema(description = "Nombre completo", example = "Juan Perez")
    private String name;

    @Column(name = "role")
    @Schema(description = "Rol del usuario", example = "admin")
    private String role;

    @Column(name = "phone")
    @Schema(description = "Telefono del usuario", example = "12345678")
    private String phone;

    @Column(name = "address")
    @Schema(description = "Ubicacion del usuario", example = "Calle 123")
    private String address;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
