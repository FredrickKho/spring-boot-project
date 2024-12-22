package com.fredrick.financial_management.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        // Log the exception for debugging
        System.out.println("Authentication failure: " + authException.getMessage());

        // Explicitly set the error message for expired or invalid tokens
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // 401 Unauthorized
        response.setContentType("application/json");

        // Return the error message in JSON format
        response.getWriter().write("{\"error\": \"Unauthorized: " + authException.getMessage() + "\"}");
//        throw new InvalidTokenHandler("Gagal dri Custom Auth Entry Point nya");
    }
}
