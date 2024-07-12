package com.foro.api.service;

import com.foro.api.domain.course.Course;
import com.foro.api.domain.course.CourseRepository;
import com.foro.api.domain.course.DtoCreateCourse;
import com.foro.api.domain.course.DtoCourseResponse;
import com.foro.api.domain.teacher.Teacher;
import com.foro.api.domain.teacher.TeacherRepository;
import com.foro.api.infra.errors.ValidationIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

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
        //TODO se tiene que hacer un metodo para comprar la categoria del curso y la especialidad del del profesor

        Course course = courseRepo.save(new Course(dtoCreateCourse, teacher));

        DtoCourseResponse dtoCurso = new DtoCourseResponse(course);
        URI url= uriComponentsBuilder.path("api-foro/course/{id}").buildAndExpand(course.getIdCourse()).toUri();
        return ResponseEntity.created(url).body(dtoCurso);
    }
}
