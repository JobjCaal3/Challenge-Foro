package com.foro.api.domain.student;

import com.foro.api.domain.user.DtoUpdateUser;
import jakarta.validation.constraints.NotNull;

public record DtoUpdateDataStudent(@NotNull Long idStudent,
                                   String fullName,
                                   String description,
                                   String phone,
                                   DtoUpdateUser user) {
}
