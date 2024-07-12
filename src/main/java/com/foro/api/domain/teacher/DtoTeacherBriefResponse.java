package com.foro.api.domain.teacher;

public record DtoTeacherBriefResponse (String fullName,
                                       SpecialtyTeacher specialty,
                                       String description){
    public DtoTeacherBriefResponse(Teacher teacher) {
        this(teacher.getFullName(), teacher.getSpecialty(), teacher.getDescription());
    }
}
