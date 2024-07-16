package com.foro.api.controller;

import com.foro.api.domain.course.CategoryCourse;
import com.foro.api.domain.course.DtoUpdateCourse;
import com.foro.api.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import com.foro.api.domain.course.DtoCreateCourse;
import com.foro.api.domain.course.DtoCourseResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

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

    @GetMapping("/list-all")
    public ResponseEntity<Page<DtoCourseResponse>> listCourse(@PageableDefault(size = 2, sort = {"name"}) Pageable pageable){
        return courseService.listCourse(pageable);
    }

    @GetMapping("/{idCourse}")
    public ResponseEntity<DtoCourseResponse> detailCourse(@PathVariable Long idCourse){
        return courseService.detailCourse(idCourse);
    }

    @GetMapping("/search/{category}")
    public ResponseEntity<List<DtoCourseResponse>> searchByCategory(@PathVariable String category){
        return courseService.searchByCategory(category);
    }

    @PutMapping("/update-course")
    @Transactional
    public ResponseEntity<DtoCourseResponse> updateCourse(@RequestBody @Valid DtoUpdateCourse dtoUpdateCourse){
        return courseService.updateCourse(dtoUpdateCourse);
    }
    //eliminar curso
    @DeleteMapping("/deleted-course/{idCourse}")
    @Transactional
    public ResponseEntity deltedCourse(@PathVariable Long idCourse){
        return courseService.deltedCourse(idCourse);
    }
}
