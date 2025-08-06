package com.doce.benta.business;

import com.doce.benta.model.Usuario;
import com.doce.benta.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioBusiness {

    @Autowired
    private final UsuarioService usuarioService;


    public void depoisDoGet(Usuario usuario) {
        usuario.setNome("teste");
    }
}
