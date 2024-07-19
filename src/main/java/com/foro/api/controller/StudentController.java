package com.foro.api.controller;

import com.foro.api.domain.student.DtoRegisterStudent;
import com.foro.api.domain.student.DtoStudentResponse;
import com.foro.api.domain.student.DtoUpdateDataStudent;
import com.foro.api.domain.user.DTOAuth.DtoAuthenticateResponse;
import com.foro.api.domain.user.DTOAuth.DtoLogin;
import com.foro.api.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("api-foro/student")
@SecurityRequirement(name = "bearer-key")
public class StudentController {
    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @SecurityRequirement(name = "It is for everyone, no login required")
    @PostMapping("/register")
    @Transactional
    @Operation(summary = "register a new Student")
    public ResponseEntity<DtoStudentResponse> registerStudent(@RequestBody @Valid DtoRegisterStudent dtoRegisterStudent,
                                                              UriComponentsBuilder uriComponentsBuilder){
        return studentService.registerStudent(dtoRegisterStudent, uriComponentsBuilder);
    }

    @GetMapping("/list-all-students")
    @Operation(summary = "list all registered students")
    public ResponseEntity<Page<DtoStudentResponse>> listStudent(@PageableDefault(size = 2, sort = {"fullName"}) Pageable pageable){
        return studentService.listStudent(pageable);
    }

    @GetMapping("/details-student/{idStudent}")
    @Operation(summary = "search for a student by id")
    public ResponseEntity<DtoStudentResponse> detailStudent(@PathVariable Long idStudent){
        return studentService.detailStudent(idStudent);
    }
    //update data student
    @PutMapping("/update-student")
    @Transactional
    @Operation(summary = "update student data")
    public ResponseEntity<DtoStudentResponse> updateStudent(@RequestBody @Valid DtoUpdateDataStudent dtoUpdateDataStudent){
        return studentService.updateStudent(dtoUpdateDataStudent);
    }
}
