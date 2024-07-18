package com.foro.api.controller;

import com.foro.api.domain.user.DTOAuth.DtoAuthenticateResponse;
import com.foro.api.domain.user.DTOAuth.DtoLogin;
import com.foro.api.domain.user.DtoRegisterUser;
import com.foro.api.service.UserService;
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

    @PostMapping("/login")
    public ResponseEntity<DtoAuthenticateResponse> login(@RequestBody @Valid DtoLogin dtoLogin){
        return userService.login(dtoLogin);
    }

    @PostMapping("/create-user")
    public ResponseEntity createUser(@RequestBody DtoRegisterUser dtoRegisterUser){
        return userService.createUser(dtoRegisterUser);
    }
    @DeleteMapping("/deleted-user/{idUser}")
    @Transactional
    public ResponseEntity deltedUser(@PathVariable Long idUser){
        return userService.deletedUser(idUser);
    }
}
