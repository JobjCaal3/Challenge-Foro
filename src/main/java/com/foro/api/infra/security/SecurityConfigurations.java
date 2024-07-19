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
                .authorizeHttpRequests(http -> {
                    http.requestMatchers("/doc/swagger-ui.html", "/v3/api-docs/**", "/doc/swagger-ui/**").permitAll();
                    // Permisos para la entidad User
                    http.requestMatchers(HttpMethod.POST, "api-foro/user/login").permitAll();
                    http.requestMatchers(HttpMethod.POST, "api-foro/user/create-user").hasAuthority("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "api-foro/user/deleted-user/{idUser}").hasAnyAuthority("STUDENT", "TEACHER", "ADMIN");

                    // Permisos para la entidad Student
                    http.requestMatchers(HttpMethod.POST, "api-foro/student/register").permitAll();
                    http.requestMatchers(HttpMethod.GET, "api-foro/student/list-all-students").hasAnyAuthority("TEACHER", "ADMIN");
                    http.requestMatchers(HttpMethod.GET, "api-foro/student/details-student/{idStudent}").hasAnyAuthority("STUDENT", "ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "api-foro/student/update-student").hasAuthority("STUDENT");

                    // Permisos para la entidad Teacher
                    http.requestMatchers(HttpMethod.POST, "api-foro/teacher/register").permitAll();
                    http.requestMatchers(HttpMethod.PUT, "api-foro/teacher/update").hasAnyAuthority("TEACHER", "ADMIN");
                    http.requestMatchers(HttpMethod.GET, "api-foro/teacher/list-all-teacher").hasAuthority("ADMIN");
                    http.requestMatchers(HttpMethod.GET, "api-foro/teacher/details-teacher/{idTeacher}").hasAnyAuthority("STUDENT", "TEACHER", "ADMIN");
                    http.requestMatchers(HttpMethod.GET, "api-foro/teacher/search-teacher-especiality/{specialty}").hasAnyAuthority("STUDENT", "TEACHER", "ADMIN");

                    // Permisos para la entidad Course
                    http.requestMatchers(HttpMethod.POST, "api-foro/course/create").hasAuthority("ADMIN");
                    http.requestMatchers(HttpMethod.GET, "api-foro/course/list-all").hasAnyAuthority("STUDENT", "TEACHER", "ADMIN");
                    http.requestMatchers(HttpMethod.GET, "api-foro/course/{idCourse}").hasAnyAuthority("STUDENT", "TEACHER", "ADMIN");
                    http.requestMatchers(HttpMethod.GET, "api-foro/course/search/{category}").hasAnyAuthority("STUDENT", "TEACHER", "ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "api-foro/course/update-course").hasAnyAuthority("TEACHER", "ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "api-foro/course/deleted-course/{idCourse}").hasAuthority("ADMIN");

                    // Permisos para la entidad Answer
                    http.requestMatchers(HttpMethod.POST, "api-foro/answer/create").hasAnyAuthority("STUDENT", "TEACHER", "ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "api-foro/answer/update-answer").hasAnyAuthority("STUDENT", "TEACHER", "ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "api-foro/answer/deleted/{idAnswer}").hasAuthority("ADMIN");

                    // Permisos para la entidad Topic
                    http.requestMatchers(HttpMethod.POST, "api-foro/topic/create").hasAnyAuthority("STUDENT", "TEACHER", "ADMIN");
                    http.requestMatchers(HttpMethod.GET, "api-foro/topic/list-all-topics").hasAnyAuthority("STUDENT", "TEACHER", "ADMIN");
                    http.requestMatchers(HttpMethod.GET, "api-foro/topic/{idTopic}").hasAnyAuthority("STUDENT", "TEACHER", "ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "api-foro/topic/update-topic").hasAnyAuthority("STUDENT", "TEACHER", "ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "api-foro/topic/deleted/{idTopic}").hasAuthority("ADMIN");

                    http.anyRequest().authenticated();
                })
                .addFilterBefore(authenticationSecurityFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
