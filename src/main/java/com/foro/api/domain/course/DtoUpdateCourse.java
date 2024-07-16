package com.foro.api.domain.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DtoUpdateCourse(@NotNull Long idCourse,
                              String name,
                              CategoryCourse category,
                              String descriptionCourse,
                              @NotNull Long teacherId) {
}
