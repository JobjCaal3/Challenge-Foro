package com.foro.api.domain.course;

import com.foro.api.domain.teacher.Teacher;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DtoCreateCourse(@NotBlank String name,
                              @NotNull CategoryCourse category,
                              @NotBlank String descriptionCourse,
                              @NotNull Long teacherId) {
}
