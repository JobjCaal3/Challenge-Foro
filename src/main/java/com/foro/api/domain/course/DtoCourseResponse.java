package com.foro.api.domain.course;

import com.foro.api.domain.teacher.DtoTeacherBriefResponse;
import com.foro.api.domain.teacher.DtoTeacherResponse;
import com.foro.api.domain.teacher.Teacher;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DtoCourseResponse(Long idCourse,
                                String name,
                                CategoryCourse category,
                                String descriptionCourse,
                                String currentStatus,
                                DtoTeacherBriefResponse teacher) {

    public DtoCourseResponse(Course course) {
        this(course.getIdCourse(),course.getName(), course.getCategory(), course.getDescriptionCourse(),
                statusCourse(course.getCurrentStatus()), new DtoTeacherBriefResponse(course.getTeacher()));
    }

    public DtoCourseResponse(Course course, Teacher teacher) {
        this(course.getIdCourse(),course.getName(), course.getCategory(), course.getDescriptionCourse(),
                statusCourse(course.getCurrentStatus()), new DtoTeacherBriefResponse(teacher));
    }


    private static String statusCourse(Boolean state){
        if(state == true){
            return "el topico esta activo";
        }
        return "el topico esta inactivo";
    }
}
