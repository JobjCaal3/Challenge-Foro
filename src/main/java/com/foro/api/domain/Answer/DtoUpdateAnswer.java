package com.foro.api.domain.Answer;

import jakarta.validation.constraints.NotNull;

public record DtoUpdateAnswer(@NotNull Long idAnswer,
                              String message) {
}
