package com.foro.api.domain.teacher;

import com.foro.api.domain.user.DtoRegisterUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DtoRegisterTeacher(@NotBlank String fullName,
                                 @NotNull SpecialtyTeacher specialty,
                                 @NotBlank String description,
                                 @NotBlank String phone,
                                 @NotNull @Valid DtoRegisterUser user){
}
