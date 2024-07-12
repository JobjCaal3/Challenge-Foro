package com.foro.api.controller;

import com.foro.api.domain.Answer.DtoAnswerResponse;
import com.foro.api.domain.Answer.DtoCreateAnswer;
import com.foro.api.service.AnswerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api-foro/answer")
public class AnswerController {
    private AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    public AnswerController() {}

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<DtoAnswerResponse> create(@RequestBody @Valid DtoCreateAnswer dtoCreateAnswer
                                                                        , UriComponentsBuilder uriComponentsBuilder){
        return answerService.create(dtoCreateAnswer, uriComponentsBuilder);
    }


}
