package com.travelbnb.config;

import com.travelbnb.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private JWTService jwtService;

    public JWTRequestFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Extract token from Authorization header
        String tokenHeader = request.getHeader("Authorization");

        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(8,tokenHeader.length()-1); // Extract token part after "Bearer "
            System.out.println(token);
            String username = jwtService.getUserName(token);
            System.out.println("Username: " + username);
            // Add username or token to request attributes if needed
        } else {
            System.out.println("Authorization header is missing or invalid");
            // Handle case where Authorization header is missing or invalid
        }

        filterChain.doFilter(request, response);
    }
}
