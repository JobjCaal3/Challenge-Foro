package com.foro.api.service;

import com.foro.api.domain.user.DTOAuth.DtoAuthenticateResponse;
import com.foro.api.domain.user.DTOAuth.DtoLogin;
import com.foro.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    @Autowired
    public UserService(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public ResponseEntity<DtoAuthenticateResponse> login(DtoLogin dtoLogin) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                dtoLogin.email(), dtoLogin.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenService.createToken(authentication);
        return ResponseEntity.ok(new DtoAuthenticateResponse(token));
    }
}
