package com.doce.benta.model;

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
public class Usuario extends AbstractModel {

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
}
