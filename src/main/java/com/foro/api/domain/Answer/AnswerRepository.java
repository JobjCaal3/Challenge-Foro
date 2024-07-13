package com.foro.api.domain.Answer;

import com.foro.api.domain.topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByTopicAndMessageStatusTrue(Topic topic);
    Optional<Answer> findByidAnswerAndMessageStatusTrue(Long idAnswer);
}
