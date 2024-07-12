package com.foro.api.domain.user;

import com.foro.api.domain.role.DtoRegisterRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DtoRegisterUser(@NotBlank  @Email String email,
                              @NotBlank @Size(min = 4, message = "la contraseña debe de tener mas de 4 caracteres")
                              String password) {
}
