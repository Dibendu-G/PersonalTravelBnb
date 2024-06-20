package com.travelbnb.config;

import com.travelbnb.entity.AppUserEntity;
import com.travelbnb.repository.AppUserRepository;
import com.travelbnb.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private AppUserRepository appUserRepository;

    public JWTRequestFilter(JWTService jwtService, AppUserRepository appUserRepository) {
        this.jwtService = jwtService;
        this.appUserRepository = appUserRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Extract token from Authorization header
        String tokenHeader = request.getHeader("Authorization");

        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(8,tokenHeader.length()-1); // Extract token part after "Bearer "
            System.out.println(token);
            String username = jwtService.getUserName(token);
            // Add username or token to request attributes
            Optional<AppUserEntity> opUsername = appUserRepository.findByUsername(username);
            if(opUsername.isPresent())
            {
                AppUserEntity appUserEntity = opUsername.get();
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(appUserEntity,null, Collections.singleton(new SimpleGrantedAuthority(appUserEntity.getRole())));
                authenticationToken.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            else {
                System.out.println("User not found");
            }

        } else {
            System.out.println("Authorization header is missing or invalid");
            // Handle case where Authorization header is missing or invalid
        }

        filterChain.doFilter(request, response);
    }
}
