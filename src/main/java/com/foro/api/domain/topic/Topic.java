package com.foro.api.domain.topic;

import com.foro.api.domain.answer.Answer;
import com.foro.api.domain.course.Course;
import com.foro.api.domain.student.Student;
import com.foro.api.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "topics")
@EqualsAndHashCode(of = "idTopic")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_topic")
    private Long idTopic;
    private String title;
    private String message;
    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "status_topic")
    private Boolean statusTopic;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "student_id", referencedColumnName = "id_student")
    private Student student;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "course_id", referencedColumnName = "id_course")
    private Course course;

    @OneToMany(mappedBy = "topic")
    private List<Answer> answer = new ArrayList<>();


}
