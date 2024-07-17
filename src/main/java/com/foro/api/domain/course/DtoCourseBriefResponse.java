package com.foro.api.domain.course;

import java.util.List;

public record DtoCourseBriefResponse(String name,
                                     CategoryCourse category) {
    public DtoCourseBriefResponse(Course course) {
        this(course.getName(), course.getCategory());
    }
}
