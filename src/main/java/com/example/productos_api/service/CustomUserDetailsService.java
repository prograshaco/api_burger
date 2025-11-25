package com.example.productos_api.service;

import com.example.productos_api.model.User;
import com.example.productos_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Asumimos que el rol viene sin el prefijo "ROLE_", así que lo agregamos si es
        // necesario
        // O simplemente usamos el rol tal cual si ya lo tiene.
        // Para simplificar, usamos el rol del usuario como autoridad.
        String role = user.getRole();
        if (role == null || role.isEmpty()) {
            role = "USER";
        }
        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role.toUpperCase();
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(), // Spring Security espera que esto esté codificado, o prefijado con {noop} si es
                                    // texto plano
                Collections.singletonList(new SimpleGrantedAuthority(role)));
    }
}
