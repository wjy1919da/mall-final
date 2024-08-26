package com.cmall.authservice.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtil tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Extract JWT from the request header
        String token = getJWTfromRequest(request);

        // Validate the token
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            // Retrieve username from the token
            String username = tokenProvider.getUsernameFromJWT(token);

            // Load user details associated with the token
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            // Create authentication token
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            // Set details for current request authentication
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Set the authentication into the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        // Continue filter execution
        filterChain.doFilter(request, response);
    }

    private String getJWTfromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
