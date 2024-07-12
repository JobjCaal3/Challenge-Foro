package com.foro.api.domain.student;

public record DtoStudentBriefResponse(String fullName) {
    public DtoStudentBriefResponse(Student student) {
        this(student.getFullName());
    }
}
