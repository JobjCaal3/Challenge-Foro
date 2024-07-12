package com.foro.api.domain.topic;

import com.foro.api.domain.course.Course;
import com.foro.api.domain.course.DtoCourseBriefResponse;
import com.foro.api.domain.student.DtoStudentBriefResponse;
import com.foro.api.domain.student.Student;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DtoTopicResponse(String title,
                               String message,
                               DtoStudentBriefResponse student,
                               DtoCourseBriefResponse course) {

    public DtoTopicResponse(Topic topic, Student student, Course course) {
        this(topic.getTitle(), topic.getMessage(), new DtoStudentBriefResponse(student), new DtoCourseBriefResponse(course));
    }

    public DtoTopicResponse(Topic topic){
        this(topic.getTitle(), topic.getMessage(), new DtoStudentBriefResponse(topic.getStudent()),
                new DtoCourseBriefResponse(topic.getCourse()));
    }
}
