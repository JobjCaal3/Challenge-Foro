package com.foro.api.domain.course;

import com.foro.api.domain.teacher.DtoTeacherBriefResponse;
import com.foro.api.domain.teacher.DtoTeacherResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DtoCourseResponse(Long idCourse,
                                String name,
                                CategoryCourse category,
                                String descriptionCourse,
                                DtoTeacherBriefResponse teacher) {
    public DtoCourseResponse(Course course) {
        this(course.getIdCourse(),course.getName(), course.getCategory(), course.getDescriptionCourse(),
                new DtoTeacherBriefResponse(course.getTeacher()));
    }
}
