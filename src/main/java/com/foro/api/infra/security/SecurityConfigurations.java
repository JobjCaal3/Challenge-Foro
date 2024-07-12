package com.foro.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private CustomUsersDetailsService customUsersDetailsService;
    private TokenService tokenService;
    @Autowired
    public SecurityConfigurations(JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint, CustomUsersDetailsService customUsersDetailsService, TokenService tokenService) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.customUsersDetailsService = customUsersDetailsService;
        this.tokenService = tokenService;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationSecurityFilter authenticationSecurityFilter(){
        return new AuthenticationSecurityFilter(customUsersDetailsService, tokenService);
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws Exception{
        httpSecurity.csrf(crsf -> crsf.disable())
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(ex ->ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http ->{
                    //http.requestMatchers("api-foro/user/login", "api-foro/teacher/register" , "api-foro/student/register").permitAll();
                    http.requestMatchers("api-foro/**").permitAll();
                    http.anyRequest().authenticated();
                })
                .addFilterBefore(authenticationSecurityFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
