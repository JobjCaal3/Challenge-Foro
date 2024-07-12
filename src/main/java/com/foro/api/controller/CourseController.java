package com.foro.api.controller;

import com.foro.api.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.foro.api.domain.course.DtoCreateCourse;
import com.foro.api.domain.course.DtoCourseResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api-foro/course")
public class CourseController {
    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<DtoCourseResponse> createCourse(@RequestBody @Valid DtoCreateCourse dtoCreateCourse,
                                                          UriComponentsBuilder uriComponentsBuilder){
        return courseService.createCourse(dtoCreateCourse, uriComponentsBuilder);

    }
}
