package com.foro.api.domain.teacher;

import com.foro.api.domain.course.Course;
import com.foro.api.domain.course.DtoCourseBriefResponse;

import java.util.List;

public record DtoTeacherDetailsResponse(String fullName,
                                        SpecialtyTeacher specialty,
                                        String descripcion,
                                        String phone,
                                        String email,
                                        List<DtoCourseBriefResponse> course) {

    public DtoTeacherDetailsResponse(Teacher teacher, List<Course> courses) {
        this(teacher.getFullName(), teacher.getSpecialty(), teacher.getDescription(), teacher.getPhone(), teacher.getUser().getEmail(),
                courses.stream().map(DtoCourseBriefResponse::new).toList());
    }
}
