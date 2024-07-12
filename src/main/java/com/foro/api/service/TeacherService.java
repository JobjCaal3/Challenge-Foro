package com.foro.api.service;

import com.foro.api.domain.role.Role;
import com.foro.api.domain.role.RoleRepository;
import com.foro.api.domain.teacher.*;
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
public class TeacherService {
    private TeacherRepository teacherRepo;
    private RoleRepository roleRepo;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private TokenService tokenService;
    @Autowired
    public TeacherService(TeacherRepository teacherRepo, RoleRepository roleRepo, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.teacherRepo = teacherRepo;
        this.roleRepo = roleRepo;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
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

    public ResponseEntity updateTeacher(DtoUpdateTeacher dtoUpdateTeacher) {
        Teacher teacher = teacherRepo.getReferenceById(dtoUpdateTeacher.id());
        teacher.UpdateDatos(dtoUpdateTeacher);
        DtoTeacherResponse datosTeacher = new DtoTeacherResponse(teacher);
        return ResponseEntity.ok(datosTeacher);
    }


}
