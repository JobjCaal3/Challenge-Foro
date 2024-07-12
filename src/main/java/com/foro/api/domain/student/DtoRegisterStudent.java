package com.foro.api.domain.student;

import com.foro.api.domain.user.DtoRegisterUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DtoRegisterStudent (@NotBlank String fullName,
                                  String description,
                                  @NotBlank String Phone,
                                  @NotNull @Valid DtoRegisterUser user){
}
