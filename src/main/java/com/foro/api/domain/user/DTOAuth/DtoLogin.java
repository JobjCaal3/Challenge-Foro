package com.foro.api.domain.user.DTOAuth;

import jakarta.validation.constraints.NotBlank;

public record DtoLogin(@NotBlank String email,
                       @NotBlank String password) {
}
