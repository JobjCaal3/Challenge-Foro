package com.foro.api.domain.teacher;

import com.foro.api.domain.user.DtoUpdateUser;
import jakarta.validation.constraints.NotNull;

public record DtoUpdateTeacher(@NotNull Long idTeacher,
                               String fullName,
                               SpecialtyTeacher specialty,
                               String description,
                               String phone,
                               DtoUpdateUser user) {
}
