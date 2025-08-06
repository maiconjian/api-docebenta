package com.doce.benta.repository;


import com.doce.benta.generic.crud.GenericCrudRepository;
import com.doce.benta.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends GenericCrudRepository<Usuario, UUID> {

    Optional<Usuario> findByLogin(String login);

//	Optional<Usuario> findByEmail(String email);
}