package com.foro.api.controller;

import com.foro.api.domain.topic.*;
import com.foro.api.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("api-foro/topic")
public class TopicController {
    private TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping("/create")
    @Transactional
    @Operation(summary = "create and register a topic")
    public ResponseEntity<DtoTopicResponse> createTopic(@RequestBody @Valid DtoRegisterTopic dtoRegisterTopic,
                                                        UriComponentsBuilder uriComponentsBuilder){
        return topicService.createTopic(dtoRegisterTopic, uriComponentsBuilder);
    }

    @GetMapping("/list-all-topics")
    @Operation(summary = "lists all topics if they are active")
    public ResponseEntity<Page<DtoListAllTopics>> listTopics(@PageableDefault(size = 9, sort = {"title"}) Pageable pageable){
        return topicService.listTopics(pageable);
    }

    @GetMapping("/{idTopic}")
    @Operation(summary = "searches for a topic and returns it only if it is active")
    public ResponseEntity<DtoDetailsTopic> topicDetails(@PathVariable Long idTopic){
        return topicService.topicDetails(idTopic);
    }

    @PutMapping("/update-topic")
    @Transactional
    @Operation(summary = "update a topic for ID")
    public ResponseEntity<DtoTopicResponse> updateTopic(@RequestBody @Valid DtoUpdateTopic dtoUpdateTopic){
        return topicService.updateTopic(dtoUpdateTopic);
    }

    @DeleteMapping("/deleted/{idTopic}")
    @Transactional
    @Operation(summary = "delted a topic for ID")
    public ResponseEntity deletedTopic(@PathVariable Long idTopic){
        return topicService.deletedTopic(idTopic);
    }
}
