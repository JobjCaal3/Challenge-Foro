package com.foro.api.service;

import com.foro.api.domain.role.Role;
import com.foro.api.domain.role.RoleRepository;
import com.foro.api.domain.student.DtoRegisterStudent;
import com.foro.api.domain.student.DtoStudentResponse;
import com.foro.api.domain.student.Student;
import com.foro.api.domain.student.StudentRepository;
import com.foro.api.domain.user.DTOAuth.DtoAuthenticateResponse;
import com.foro.api.domain.user.DTOAuth.DtoLogin;
import com.foro.api.domain.user.User;
import com.foro.api.infra.errors.ValidationIntegration;
import com.foro.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class StudentService {
    private StudentRepository studentRepo;
    private RoleRepository roleRepo;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public StudentService(StudentRepository studentRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.studentRepo = studentRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<DtoStudentResponse> registerStudent(DtoRegisterStudent dtoRegisterStudent,
                                                              UriComponentsBuilder uriComponentsBuilder) {
        Role existRole = roleRepo.findByNameRole("STUDENT").orElseThrow(()-> new ValidationIntegration("el rol no existe"));

        User user = new User(dtoRegisterStudent.user(), existRole);
        user.setPassword(passwordEncoder.encode(dtoRegisterStudent.user().password()));

        Student student = studentRepo.save(new Student(dtoRegisterStudent, user));

        DtoStudentResponse dtoStudent = new DtoStudentResponse(student);
        URI url = uriComponentsBuilder.path("api-foro/student/{id}").buildAndExpand(student.getIdStudent()).toUri();
        return ResponseEntity.created(url).body(dtoStudent);

    }
}
