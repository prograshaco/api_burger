package com.example.productos_api.controller;

import com.example.productos_api.config.JwtTokenProvider;
import com.example.productos_api.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "API para obtener tokens de acceso")
public class AuthController {

    @Autowired
    private com.example.productos_api.repository.UserRepository userRepository;

    @Operation(summary = "Iniciar sesión", description = "Autentica al usuario y retorna un token simple")
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        // Validar credenciales manualmente
        com.example.productos_api.model.User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElse(null);
        
        if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Credenciales inválidas"));
        }

        // Generar un token simple (username:timestamp)
        String simpleToken = user.getUsername() + ":" + System.currentTimeMillis();

        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", simpleToken);
        response.put("tokenType", "Simple");
        response.put("user", user);

        return ResponseEntity.ok(response);
    }

    // Clase interna DTO para el request de login
    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
