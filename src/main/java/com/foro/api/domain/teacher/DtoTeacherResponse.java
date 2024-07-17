package com.foro.api.domain.teacher;

import com.foro.api.domain.course.Course;

public record DtoTeacherResponse(Long idTeacher,
                                 String fullName,
                                 SpecialtyTeacher specialty,
                                 String descripcion,
                                 String phone,
                                 String email) {

    public DtoTeacherResponse(Teacher teacher) {
        this(teacher.getIdTeacher(), teacher.getFullName(), teacher.getSpecialty(),teacher.getDescription(),
                teacher.getPhone(), teacher.getUser().getEmail());
    }

}
