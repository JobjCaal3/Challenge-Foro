package com.foro.api.controller;

import com.foro.api.domain.course.CategoryCourse;
import com.foro.api.domain.course.DtoUpdateCourse;
import com.foro.api.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "crea y registra un nuevo curso solo lo puede hacer un admin")
    public ResponseEntity<DtoCourseResponse> createCourse(@RequestBody @Valid DtoCreateCourse dtoCreateCourse,
                                                          UriComponentsBuilder uriComponentsBuilder){
        return courseService.createCourse(dtoCreateCourse, uriComponentsBuilder);
    }

    @GetMapping("/list-all")
    @Operation(summary = "all active courses are listed")
    public ResponseEntity<Page<DtoCourseResponse>> listCourse(@PageableDefault(size = 2, sort = {"name"}) Pageable pageable){
        return courseService.listCourse(pageable);
    }

    @GetMapping("/{idCourse}")
    @Operation(summary = "searches for a course by its id and returns the response only if it is active")
    public ResponseEntity<DtoCourseResponse> detailCourse(@PathVariable Long idCourse){
        return courseService.detailCourse(idCourse);
    }

    @GetMapping("/search/{category}")
    @Operation(summary = "searches for a course by its category and returns it only if it is active")
    public ResponseEntity<List<DtoCourseResponse>> searchByCategory(@PathVariable String category){
        return courseService.searchByCategory(category);
    }

    @PutMapping("/update-course")
    @Transactional
    @Operation(summary = "update a course")
    public ResponseEntity<DtoCourseResponse> updateCourse(@RequestBody @Valid DtoUpdateCourse dtoUpdateCourse){
        return courseService.updateCourse(dtoUpdateCourse);
    }
    //eliminar curso
    @DeleteMapping("/deleted-course/{idCourse}")
    @Transactional
    @Operation(summary = "deleted a course")
    public ResponseEntity deltedCourse(@PathVariable Long idCourse){
        return courseService.deltedCourse(idCourse);
    }
}
