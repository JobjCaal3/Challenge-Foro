package com.foro.api.service;

import com.foro.api.domain.course.*;
import com.foro.api.domain.teacher.Teacher;
import com.foro.api.domain.teacher.TeacherRepository;
import com.foro.api.domain.topic.DtoListAllTopics;
import com.foro.api.infra.errors.ValidationIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CourseService {
    private CourseRepository courseRepo;
    private TeacherRepository teacherRepo;

    @Autowired
    public CourseService(CourseRepository courseRepo, TeacherRepository teacherRepo) {
        this.courseRepo = courseRepo;
        this.teacherRepo = teacherRepo;
    }


    public ResponseEntity<DtoCourseResponse> createCourse(DtoCreateCourse dtoCreateCourse,
                                                          UriComponentsBuilder uriComponentsBuilder) {
        Teacher teacher = teacherRepo.findById(dtoCreateCourse.teacherId())
                .orElseThrow(() -> new ValidationIntegration("el teacher que ingreso no existe"));
        Course course = courseRepo.save(new Course(dtoCreateCourse, teacher));
        URI url= uriComponentsBuilder.path("api-foro/course/{id}").buildAndExpand(course.getIdCourse()).toUri();
        return ResponseEntity.created(url).body(new DtoCourseResponse(course));
    }

    public ResponseEntity<Page<DtoCourseResponse>> listCourse(Pageable pageable) {
        var page = courseRepo.findAllByCurrentStatusTrue(pageable).map(DtoCourseResponse::new);
        return ResponseEntity.ok(page);
    }

    public ResponseEntity<DtoCourseResponse> detailCourse(Long idCourse) {
        Course course = courseRepo.findByidCourseAndCurrentStatusTrue(idCourse).orElseThrow(()->new ValidationIntegration("no se encontro el curso o esta inactivo"));
        return ResponseEntity.ok(new DtoCourseResponse(course));
    }

    public ResponseEntity<List<DtoCourseResponse>> searchByCategory(String category) {
        List<DtoCourseResponse> courseResponses = courseRepo.findByCategoryIgnoreCaseJPQL(category)
                .stream().map(DtoCourseResponse::new).toList();

        if (courseResponses == null || courseResponses.isEmpty()) {
            throw new ValidationIntegration("la categoria por la que se esta filtrando no existe");
        }
        return ResponseEntity.ok(courseResponses);
    }

    public ResponseEntity<DtoCourseResponse> updateCourse(DtoUpdateCourse dtoUpdateCourse) {
        Course course = courseRepo.findById(dtoUpdateCourse.idCourse()).orElseThrow(() -> new ValidationIntegration("el curso que ingreso no existe"));
        Teacher teacher = teacherRepo.findById(dtoUpdateCourse.teacherId()).orElseThrow(() -> new ValidationIntegration("el teacher que ingreso no existe"));
        course.updateCourse(dtoUpdateCourse, teacher);
        return ResponseEntity.ok(new DtoCourseResponse(course, teacher));
    }

    public ResponseEntity deltedCourse(Long idCourse) {
        Course course = courseRepo.findById(idCourse).orElseThrow(() -> new ValidationIntegration("el curso que ingreso no existe"));
        course.deltedCourse(idCourse);
        return ResponseEntity.noContent().build();
    }
}
