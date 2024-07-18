package com.foro.api.service;

import com.foro.api.domain.role.Role;
import com.foro.api.domain.role.RoleRepository;
import com.foro.api.domain.user.DTOAuth.DtoAuthenticateResponse;
import com.foro.api.domain.user.DTOAuth.DtoLogin;
import com.foro.api.domain.user.DtoRegisterUser;
import com.foro.api.domain.user.User;
import com.foro.api.domain.user.UserRepository;
import com.foro.api.infra.errors.ValidationIntegration;
import com.foro.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private RoleRepository roleRepo;
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(AuthenticationManager authenticationManager, TokenService tokenService, RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<DtoAuthenticateResponse> login(DtoLogin dtoLogin) {
        User user = userRepo.findByEmail(dtoLogin.email()).orElseThrow(()->new ValidationIntegration("user not found"));
        if (!user.getIsAccountNonLocked()){
            throw new LockedException("this account is bloked");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                dtoLogin.email(), dtoLogin.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenService.createToken(authentication);
        return ResponseEntity.ok(new DtoAuthenticateResponse(token));
    }

    public ResponseEntity createUser(DtoRegisterUser dtoRegisterUser) {
        Role role = roleRepo.findByNameRole("ADMIN").orElseThrow(()->new ValidationIntegration("the rol no found"));
        User user = new User(dtoRegisterUser, role);
        user.setPassword(passwordEncoder.encode(dtoRegisterUser.password()));
        userRepo.save(user);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity deletedUser(Long idUser) {
        User user = userRepo.findById(idUser).orElseThrow(()->new ValidationIntegration("id user not found o not exist"));
        user.deletedUser();
        return ResponseEntity.noContent().build();
    }
}
