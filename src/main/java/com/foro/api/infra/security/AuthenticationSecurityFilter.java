package com.foro.api.infra.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.apache.tomcat.util.descriptor.web.ContextHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class AuthenticationSecurityFilter extends OncePerRequestFilter {
    private CustomUsersDetailsService customUsersDetailsService;
    private TokenService tokenService;

    @Autowired
    public AuthenticationSecurityFilter(CustomUsersDetailsService customUsersDetailsService, TokenService tokenService) {
        this.customUsersDetailsService = customUsersDetailsService;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        String token = getRequestToken(request);
        if (StringUtils.hasText(token)  && tokenService.validateToken(token)){
            String email = tokenService.extractUsername(token);
            UserDetails userDetails = customUsersDetailsService.loadUserByUsername(email);
            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            if (roles.contains("TEACHER") || roles.contains("STUDENT")||roles.contains("ADMIN")){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                        (userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getRequestToken(HttpServletRequest request) {
        String bearToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearToken ) && bearToken.startsWith("Bearer ")) {
            return bearToken.substring(7, bearToken.length());
        }
        return null;
    }
}
