package com.doce.benta.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record DadosRefreshToken(@NotBlank String refreshToken) {
}
