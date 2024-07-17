package com.foro.api.controller;

import com.foro.api.domain.teacher.*;
import com.foro.api.domain.user.DTOAuth.DtoAuthenticateResponse;
import com.foro.api.domain.user.DTOAuth.DtoLogin;
import com.foro.api.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

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

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<DtoTeacherResponse> updateTeacher(@RequestBody @Valid DtoUpdateTeacher dtoUpdateTeacher){
        return teacherService.updateTeacher(dtoUpdateTeacher);
    }

    @GetMapping("/list-all-teacher")
    public ResponseEntity<Page<DtoTeacherResponse>> listTeacher(@PageableDefault(size = 2, sort = {"fullName"}) Pageable pageable){
        return teacherService.listTeacher(pageable);
    }

    @GetMapping("/details-teacher/{idTeacher}")
    public ResponseEntity<DtoTeacherDetailsResponse> detailTeacher(@PathVariable Long idTeacher){
        return teacherService.detailTeacher(idTeacher);
    }

    @GetMapping("/search-teacher-especiality/{specialty}")
    public ResponseEntity<List<DtoTeacherResponse>> searchEspeciality(@PathVariable String specialty){
        return teacherService.searchEspeciality(specialty);
    }
}
