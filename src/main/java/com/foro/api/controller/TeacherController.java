package com.foro.api.controller;

import com.foro.api.domain.teacher.DtoRegisterTeacher;
import com.foro.api.domain.teacher.DtoTeacherResponse;
import com.foro.api.domain.teacher.DtoUpdateTeacher;
import com.foro.api.domain.user.DTOAuth.DtoAuthenticateResponse;
import com.foro.api.domain.user.DTOAuth.DtoLogin;
import com.foro.api.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("api-foro/teacher")
public class TeacherController {
    private TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<DtoTeacherResponse> registerTeacher(@RequestBody @Valid DtoRegisterTeacher dtoRegisterTeacher,
                                                              UriComponentsBuilder uriComponentsBuilder){
        return teacherService.registerTeacher(dtoRegisterTeacher, uriComponentsBuilder);

    }

    //actualizar los datos de teacher
    @PutMapping("/update")
    @Transactional
    public ResponseEntity updateTeacher(@RequestBody @Valid DtoUpdateTeacher dtoUpdateTeacher){
        return teacherService.updateTeacher(dtoUpdateTeacher);
    }


    //obtener todos los profesores
    //@GetMapping

    //obtener los datos de un solo profesor

    //desactivar un profesor
    //@DeleteMapping

}
