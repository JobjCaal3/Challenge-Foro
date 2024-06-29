package com.foro.api.domain.answer;

import com.foro.api.domain.topic.Topic;
import com.foro.api.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "answers")
@EqualsAndHashCode(of = "idAnswer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_answer")
    private Long idAnswer;
    private String message;
    @Column(name = "message_status")
    private Boolean messageStatus;
    @Column(name = "creation_date")
    private Date creationDate;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "topic_id", referencedColumnName = "id_topic")
    private Topic topic;

}
