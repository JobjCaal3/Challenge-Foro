package com.foro.api.controller;

import com.foro.api.domain.student.DtoRegisterStudent;
import com.foro.api.domain.student.DtoStudentResponse;
import com.foro.api.domain.user.DTOAuth.DtoAuthenticateResponse;
import com.foro.api.domain.user.DTOAuth.DtoLogin;
import com.foro.api.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("api-foro/student")
public class StudentController {
    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<DtoStudentResponse> registerStudent(@RequestBody @Valid DtoRegisterStudent dtoRegisterStudent,
                                                              UriComponentsBuilder uriComponentsBuilder){
        return studentService.registerStudent(dtoRegisterStudent, uriComponentsBuilder);
    }
}
