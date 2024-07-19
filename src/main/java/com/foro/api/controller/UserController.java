package com.foro.api.controller;

import com.foro.api.domain.user.DTOAuth.DtoAuthenticateResponse;
import com.foro.api.domain.user.DTOAuth.DtoLogin;
import com.foro.api.domain.user.DtoRegisterUser;
import com.foro.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api-foro/user")
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @SecurityRequirement(name = "It is for everyone, no login required")
    @PostMapping("/login")
    @Operation(summary = "be able to log in and get a token")
    public ResponseEntity<DtoAuthenticateResponse> login(@RequestBody @Valid DtoLogin dtoLogin){
        return userService.login(dtoLogin);
    }

    @SecurityRequirement(name = "bearer-key")
    @PostMapping("/create-user")
    @Operation(summary = "register a new user")
    public ResponseEntity createUser(@RequestBody DtoRegisterUser dtoRegisterUser){
        return userService.createUser(dtoRegisterUser);
    }
    @SecurityRequirement(name = "bearer-key")
    @DeleteMapping("/deleted-user/{idUser}")
    @Transactional
    @Operation(summary = "deleted a user")
    public ResponseEntity deltedUser(@PathVariable Long idUser){
        return userService.deletedUser(idUser);
    }
}
