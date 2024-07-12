package com.foro.api.domain.topic;

import com.foro.api.domain.course.DtoCourseBriefResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DtoUpdateTopic(@NotNull Long idTopic,
                             String title,
                             String message) { }
