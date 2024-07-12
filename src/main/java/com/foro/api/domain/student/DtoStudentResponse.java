package com.foro.api.domain.student;

public record DtoStudentResponse(Long idStudent,
                                 String fullName,
                                 String descripcion,
                                 String phone,
                                 String email) {
    public DtoStudentResponse(Student student) {
        this(student.getIdStudent(), student.getFullName(), student.getDescription(), student.getPhone(),
                student.getUser().getEmail());
    }
}
