package com.doce.benta.model;

import com.doce.benta.dto.DadosCadastroUsuario;
import com.doce.benta.generic.crud.AbstractModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
public class Usuario extends AbstractModel implements UserDetails {

    @Column
    private String nome;

    @Column
    private String cpf;

    @Column
    private String login;

    @Column
    private String senha;

    @Column
    private String email;

    public Usuario(){}

    public Usuario(DadosCadastroUsuario dados, String senhaCriptogravada) {
        this.email = dados.email();
        this.nome = dados.nome();
        this.senha = senhaCriptogravada;
        this.cpf= dados.cpf();
        this.login = dados.login();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
