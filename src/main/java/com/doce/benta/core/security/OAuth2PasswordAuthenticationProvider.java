package com.doce.benta.core.security;

import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

public class OAuth2PasswordAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final RegisteredClientRepository registeredClientRepository;

    public OAuth2PasswordAuthenticationProvider(AuthenticationManager authenticationManager,
                                                JwtEncoder jwtEncoder,
                                                RegisteredClientRepository registeredClientRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
        this.registeredClientRepository = registeredClientRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest = (UsernamePasswordAuthenticationToken) authentication;
        Authentication authResult = authenticationManager.authenticate(authRequest);

        if (!authResult.isAuthenticated()) {
            throw new BadCredentialsException("Invalid credentials");
        }

        RegisteredClient registeredClient = registeredClientRepository.findByClientId("floripark");
        if (registeredClient == null) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
        }

        // Cria o JWT
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(authResult.getName())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(24, ChronoUnit.HOURS))
                .claim("authorities", authResult.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .build();

        Jwt jwt = jwtEncoder.encode(JwtEncoderParameters.from(claims));

        // Cria o token de acesso OAuth2
        OAuth2AccessToken accessToken = new OAuth2AccessToken(
                OAuth2AccessToken.TokenType.BEARER,
                jwt.getTokenValue(),
                jwt.getIssuedAt(),
                jwt.getExpiresAt()
        );

        Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();

        return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
