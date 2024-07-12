package com.foro.api.domain.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Optional<Topic> findByTitle(String title);
    Optional<Topic> findByMessage(String message);
    Page<Topic> findAllByStatusTopicTrue(Pageable page);
    Optional<Topic> findByIdTopicAndStatusTopicTrue(Long idTopic);
}
