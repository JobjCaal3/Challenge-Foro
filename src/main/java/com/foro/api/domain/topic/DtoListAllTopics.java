package com.foro.api.domain.topic;

import com.foro.api.domain.course.Course;
import com.foro.api.domain.course.DtoCourseBriefResponse;
import com.foro.api.domain.student.DtoStudentBriefResponse;
import com.foro.api.domain.student.Student;

import java.time.LocalDateTime;

public record DtoListAllTopics(String title,
                               String message,
                               LocalDateTime creationDate,
                               String state,
                               DtoStudentBriefResponse student,
                               DtoCourseBriefResponse course) {

    public DtoListAllTopics(Topic topic){
        this(topic.getTitle(), topic.getMessage(), topic.getCreationDate(), statusTopic(topic.getStatusTopic())
                ,new DtoStudentBriefResponse(topic.getStudent()), new DtoCourseBriefResponse(topic.getCourse()));
    }

    private static String statusTopic(Boolean state){
        if(state == true){
            return "el topico esta activo";
        }
        return "el topico esta inactivo";
    }
}
