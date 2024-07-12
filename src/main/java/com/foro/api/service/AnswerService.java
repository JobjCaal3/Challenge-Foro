package com.foro.api.service;

import com.foro.api.domain.Answer.Answer;
import com.foro.api.domain.Answer.AnswerRepository;
import com.foro.api.domain.Answer.DtoAnswerResponse;
import com.foro.api.domain.Answer.DtoCreateAnswer;
import com.foro.api.domain.topic.Topic;
import com.foro.api.domain.topic.TopicRepository;
import com.foro.api.domain.user.User;
import com.foro.api.domain.user.UserRepository;
import com.foro.api.infra.errors.ValidationIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@Service
public class AnswerService {
    private AnswerRepository answerRepo;
    private UserRepository userRepo;
    private TopicRepository topicRepo;

    @Autowired
    public AnswerService(AnswerRepository answerRepo, UserRepository userRepo, TopicRepository topicRepo) {
        this.answerRepo = answerRepo;
        this.userRepo = userRepo;
        this.topicRepo = topicRepo;
    }

    public AnswerService() {}

    public ResponseEntity<DtoAnswerResponse> create(DtoCreateAnswer dtoCreateAnswer,
                                                    UriComponentsBuilder uriComponentsBuilder) {
        User user = userRepo.findById(dtoCreateAnswer.idUser())
                .orElseThrow(() -> new ValidationIntegration("usuario no encontrado"));
        Topic topic = topicRepo.findById(dtoCreateAnswer.idTopic())
                .orElseThrow(() -> new ValidationIntegration("topico no encontrado"));

        Answer answer =  answerRepo.save(new Answer(dtoCreateAnswer.message(), user, topic));

        DtoAnswerResponse response = new DtoAnswerResponse(answer, user);
        URI uri = uriComponentsBuilder.path("/api-foro/answer/{id}").buildAndExpand(answer.getIdAnswer()).toUri();
        return ResponseEntity.created(uri).body(response);
    }
}
