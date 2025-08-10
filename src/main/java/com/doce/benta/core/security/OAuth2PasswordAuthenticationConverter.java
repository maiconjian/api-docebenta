package com.doce.benta.core.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

public class OAuth2PasswordAuthenticationConverter implements AuthenticationConverter {

    @Override
    public Authentication convert(HttpServletRequest request) {
        if (!"password".equals(request.getParameter("grant_type"))) {
            return null;
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String clientId = request.getParameter("client_id");

        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
