package com.example.productos_api.repository;

import com.example.productos_api.config.TursoClient;
import com.example.productos_api.model.User;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository {

    @Autowired
    private TursoClient tursoClient;

    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        JsonNode response = tursoClient.executeQuery(sql, null);
        return parseUsers(response);
    }

    public Optional<User> findById(String id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        JsonNode response = tursoClient.executeQuery(sql, List.of(id));
        List<User> users = parseUsers(response);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        JsonNode response = tursoClient.executeQuery(sql, List.of(username));
        List<User> users = parseUsers(response);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    public User save(User user) {
        if (user.getId() == null || user.getId().isEmpty()) {
            user.setId(UUID.randomUUID().toString());
        }

        String checkSql = "SELECT COUNT(*) as count FROM users WHERE id = ?";
        JsonNode checkResponse = tursoClient.executeQuery(checkSql, List.of(user.getId()));

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
            String sql = "UPDATE users SET username = ?, email = ?, password = ?, name = ?, role = ?, phone = ?, address = ? WHERE id = ?";
            tursoClient.executeQuery(sql, List.of(
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getName(),
                    user.getRole(),
                    user.getPhone(),
                    user.getAddress(),
                    user.getId()));
        } else {
            String sql = "INSERT INTO users (id, username, email, password, name, role, phone, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            tursoClient.executeQuery(sql, List.of(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getName(),
                    user.getRole(),
                    user.getPhone(),
                    user.getAddress()));
        }

        return user;
    }

    public void deleteById(String id) {
        String sql = "DELETE FROM users WHERE id = ?";
        tursoClient.executeQuery(sql, List.of(id));
    }

    private List<User> parseUsers(JsonNode response) {
        List<User> users = new ArrayList<>();
        try {
            JsonNode results = response.get(0).get("results");
            JsonNode columns = results.get("columns");
            JsonNode rows = results.get("rows");

            if (rows.isArray()) {
                for (JsonNode row : rows) {
                    User user = new User();
                    for (int i = 0; i < columns.size(); i++) {
                        String columnName = columns.get(i).asText();
                        JsonNode value = row.get(i);

                        switch (columnName) {
                            case "id" -> user.setId(value.asText());
                            case "username" -> user.setUsername(value.asText());
                            case "email" -> user.setEmail(value.isNull() ? null : value.asText());
                            case "password" -> user.setPassword(value.asText());
                            case "name" -> user.setName(value.isNull() ? null : value.asText());
                            case "role" -> user.setRole(value.isNull() ? null : value.asText());
                            case "phone" -> user.setPhone(value.isNull() ? null : value.asText());
                            case "address" -> user.setAddress(value.isNull() ? null : value.asText());
                        }
                    }
                    users.add(user);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error parsing users: " + e.getMessage(), e);
        }
        return users;
    }
}
