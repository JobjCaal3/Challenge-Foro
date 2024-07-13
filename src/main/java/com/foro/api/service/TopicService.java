package com.foro.api.service;

import com.foro.api.domain.Answer.Answer;
import com.foro.api.domain.Answer.AnswerRepository;
import com.foro.api.domain.course.Course;
import com.foro.api.domain.course.CourseRepository;
import com.foro.api.domain.student.Student;
import com.foro.api.domain.student.StudentRepository;
import com.foro.api.domain.topic.*;
import com.foro.api.infra.errors.ValidationIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class TopicService {
    private TopicRepository topicRepo;
    private StudentRepository studentRepo;
    private CourseRepository courseRepo;
    private AnswerRepository answerRepo;
    @Autowired
    public TopicService(TopicRepository topicRepo, StudentRepository studentRepo, CourseRepository courseRepo, AnswerRepository answerRepo) {
        this.topicRepo = topicRepo;
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
        this.answerRepo = answerRepo;
    }

    public ResponseEntity<DtoTopicResponse> createTopic(DtoRegisterTopic dtoRegisterTopic,
                                                        UriComponentsBuilder uriComponentsBuilder) {
        Student student = studentRepo.findById(dtoRegisterTopic.idStudent())
                .orElseThrow(()->new ValidationIntegration("el estudiante no existe"));
        Course course = courseRepo.findById(dtoRegisterTopic.idCourse())
                .orElseThrow(()-> new ValidationIntegration("el curso no existe"));
        //comprobacion de que el titulo y el masaje ya existen
        Optional titleVerification = topicRepo.findByTitle(dtoRegisterTopic.title());
        Optional messageVerification = topicRepo.findByMessage(dtoRegisterTopic.message());

        if (titleVerification.isPresent() && messageVerification.isPresent()){
            throw new ValidationIntegration("el titulo del topico y el mensaje ya existen intente con otro nombre o buscque el topico para econtrar respuesta a su duda");
        }

        Topic topic = topicRepo.save(new Topic(dtoRegisterTopic, student, course));

        DtoTopicResponse dtoTopic = new DtoTopicResponse(topic, student, course);
        URI url= uriComponentsBuilder.path("api-foro/topic/{id}").buildAndExpand(topic.getIdTopic()).toUri();
        return ResponseEntity.created(url).body(dtoTopic);
    }

    public ResponseEntity<Page<DtoListAllTopics>> listTopics(Pageable pageable) {
        var page = topicRepo.findAllByStatusTopicTrue(pageable).map(DtoListAllTopics::new);
        return ResponseEntity.ok(page);
    }

    public ResponseEntity<DtoDetailsTopic> topicDetails(Long idTopic) {
        Topic topic = topicRepo.findByIdTopicAndStatusTopicTrue(idTopic).orElseThrow(()->new ValidationIntegration("el topico no existe o esta inactivo"));
        List<Answer> answer = answerRepo.findByTopicAndMessageStatusTrue(topic);
        return ResponseEntity.ok(new DtoDetailsTopic(topic, answer));
    }

    public ResponseEntity<DtoTopicResponse> updateTopic(DtoUpdateTopic dtoUpdateTopic) {
        Topic topic = topicRepo.findByIdTopicAndStatusTopicTrue(dtoUpdateTopic.idTopic()).orElseThrow(()->new ValidationIntegration("el topico no existe o esta inactivo"));
        topic.UpdateDataTopic(dtoUpdateTopic);
        return ResponseEntity.ok(new DtoTopicResponse(topic));
    }

    public ResponseEntity deletedTopic(Long idTopic) {
        Topic topic = topicRepo.getReferenceById(idTopic);
        topic.deleted();
        return ResponseEntity.noContent().build();
    }
}
