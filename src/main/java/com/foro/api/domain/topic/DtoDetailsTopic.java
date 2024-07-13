package com.foro.api.domain.topic;

import com.foro.api.domain.Answer.Answer;
import com.foro.api.domain.Answer.DtoAnswerResponse;
import com.foro.api.domain.course.DtoCourseBriefResponse;
import com.foro.api.domain.student.DtoStudentBriefResponse;

import java.time.LocalDateTime;
import java.util.List;

public record DtoDetailsTopic(String title,
                              String message,
                              LocalDateTime creationDate,
                              String state,
                              DtoStudentBriefResponse student,
                              DtoCourseBriefResponse course,
                              List<DtoAnswerResponse> answers) {

    public DtoDetailsTopic(Topic topic, List<Answer> answers){
        this(topic.getTitle(), topic.getMessage(), topic.getCreationDate(), statusTopic(topic.getStatusTopic())
                ,new DtoStudentBriefResponse(topic.getStudent()), new DtoCourseBriefResponse(topic.getCourse()),
                answers.stream().map(DtoAnswerResponse::new).toList());
    }

    private static String statusTopic(Boolean state){
        return state ? "El tópico está activo" : "El tópico está inactivo";
    }
}
