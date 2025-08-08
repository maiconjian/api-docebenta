package com.doce.benta.service;


import com.doce.benta.repository.UsuarioRepository;
import com.doce.benta.core.exception.RegraDeNegocioException;
import com.doce.benta.dto.DadosCadastroUsuario;
import com.doce.benta.generic.Dao;
import com.doce.benta.generic.crud.AbstractServiceDao;
import com.doce.benta.model.Usuario;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UsuarioService extends AbstractServiceDao<Usuario, UsuarioRepository> implements UserDetailsService {


    @Autowired
    private Dao dao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @SuppressWarnings("unchecked")
    public List<HashMap<String, Object>> list() {
        String sql = """
                select * from usuario u
                """;
        Query q = dao.createNativeQuery(sql);

        return dao.returnMappedQueryList(q);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario não foi encontrado"));
    }

    @Transactional
    public Usuario cadastrar(DadosCadastroUsuario dados) {
        if (!dados.senha().equals(dados.confirmarSenha())) {
            throw new RegraDeNegocioException("");
        }
        var senhaCriptogravada = passwordEncoder.encode(dados.senha());
        var usuario = new Usuario(dados, senhaCriptogravada);
        return repository.save(usuario);
    }
}