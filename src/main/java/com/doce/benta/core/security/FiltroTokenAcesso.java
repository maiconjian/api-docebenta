package com.doce.benta.core.security;

import com.doce.benta.model.Usuario;
import com.doce.benta.repository.UsuarioRepository;
import com.doce.benta.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FiltroTokenAcesso extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //recuperar token
        String token = recuperarTokenRequisicao(request);

        if(token != null){
            String login = tokenService.verificarToken(token);
            Usuario usuario = usuarioRepository.findByLogin(login).orElseThrow();

            Authentication authentication = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request,response);
    }

    private String recuperarTokenRequisicao(HttpServletRequest request) {
        var authoriazationHeader = request.getHeader("Authorization");
        if(authoriazationHeader != null){
            return authoriazationHeader.replace("Bearer ","");
        }
        return null;
    }
}
