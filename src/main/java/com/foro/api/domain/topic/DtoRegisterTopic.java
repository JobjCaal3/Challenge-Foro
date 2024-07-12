package com.foro.api.domain.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DtoRegisterTopic(@NotBlank String title,
                               @NotBlank String message,
                               @NotNull Long idStudent,
                               @NotNull Long idCourse) {
}
