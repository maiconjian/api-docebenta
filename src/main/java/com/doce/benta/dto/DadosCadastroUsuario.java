package com.doce.benta.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroUsuario(
        @NotBlank String email,
        @NotBlank String senha,
        @NotBlank String confirmarSenha,
        @NotBlank String nome,
        @NotBlank String login,
        @NotBlank String cpf,
        Long matricula
) {
}
