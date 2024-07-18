package com.foro.api.service;

import com.foro.api.domain.course.Course;
import com.foro.api.domain.course.CourseRepository;
import com.foro.api.domain.role.Role;
import com.foro.api.domain.role.RoleRepository;
import com.foro.api.domain.teacher.*;
import com.foro.api.domain.user.User;
import com.foro.api.infra.errors.ValidationIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class TeacherService {
    private TeacherRepository teacherRepo;
    private RoleRepository roleRepo;
    private PasswordEncoder passwordEncoder;
    private CourseRepository courseRepo;
    @Autowired
    public TeacherService(TeacherRepository teacherRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder, CourseRepository courseRepo) {
        this.teacherRepo = teacherRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.courseRepo = courseRepo;
    }

    public ResponseEntity<DtoTeacherResponse> registerTeacher(DtoRegisterTeacher dtoRegisterTeacher,
                                                              UriComponentsBuilder uriComponentsBuilder) {
        Role existRole = roleRepo.findByNameRole("TEACHER").orElseThrow(() -> new ValidationIntegration("el rol no existe"));

        User user = new User(dtoRegisterTeacher.user(), existRole);
        user.setPassword(passwordEncoder.encode(dtoRegisterTeacher.user().password()));

        Teacher teacher = teacherRepo.save(new Teacher(dtoRegisterTeacher, user));

        DtoTeacherResponse dtoTeacher = new DtoTeacherResponse(teacher);
        URI url = uriComponentsBuilder.path("api-foro/teacher/{id}").buildAndExpand(teacher.getIdTeacher()).toUri();
        return ResponseEntity.created(url).body(dtoTeacher);
    }

    public ResponseEntity<DtoTeacherResponse> updateTeacher(DtoUpdateTeacher dtoUpdateTeacher) {
        Teacher teacher = teacherRepo.findById(dtoUpdateTeacher.idTeacher()).orElseThrow(()->new ValidationIntegration("teacher not found"));
        teacher.getUser().setPassword(passwordEncoder.encode(dtoUpdateTeacher.user().password()));
        teacher.UpdateTeacher(dtoUpdateTeacher);
        return ResponseEntity.ok(new DtoTeacherResponse(teacher));
    }

    public ResponseEntity<Page<DtoTeacherResponse>> listTeacher(Pageable pageable) {
        Page<DtoTeacherResponse> page = teacherRepo.listAllTeacher(pageable).map(DtoTeacherResponse::new);
        return ResponseEntity.ok(page);
    }

    public ResponseEntity<DtoTeacherDetailsResponse> detailTeacher(Long idTeacher) {
        Teacher teacher = teacherRepo.detailTeacherActivo(idTeacher).orElseThrow(()->new ValidationIntegration("Teacher not found or is inactive"));;
        List<Course> courses = courseRepo.searchCourseActivo(idTeacher);
        return ResponseEntity.ok(new DtoTeacherDetailsResponse(teacher, courses));
    }

    public ResponseEntity<List<DtoTeacherResponse>> searchEspeciality(String specialty) {
        List<DtoTeacherResponse> teacherResponses = teacherRepo.searchEspecialityTeacher(specialty)
                .stream().map(DtoTeacherResponse::new).toList();
        if(teacherResponses.isEmpty() || teacherResponses == null){
            throw new ValidationIntegration("la especialidad por la que esta filtrando no existe");
        }
        return ResponseEntity.ok(teacherResponses);
    }

}
