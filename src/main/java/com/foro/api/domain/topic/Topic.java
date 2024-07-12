package com.foro.api.domain.topic;

import com.foro.api.domain.Answer.Answer;
import com.foro.api.domain.course.Course;
import com.foro.api.domain.student.Student;
import jakarta.persistence.*;
import lombok.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private LocalDateTime creationDate;
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


    public Topic(DtoRegisterTopic dtoRegisterTopic, Student student, Course course) {
        this.title = dtoRegisterTopic.title();
        this.message = dtoRegisterTopic.message();
        this.student = student;
        this.course = course;
        this.creationDate = LocalDateTime.now();
        this.statusTopic = true;
    }

    public void UpdateDataTopic(DtoUpdateTopic dtoUpdateTopic) {
        if (dtoUpdateTopic.title() != null) this.title = dtoUpdateTopic.title();
        if (dtoUpdateTopic.message() != null )this.message = dtoUpdateTopic.message();
    }

    public void deleted(){
        this.statusTopic = false;
    }

    //para darle formato a la fecha de creacion, esta se convierte en un string y deja de ser como tal un date
    public String getFormattedCreationDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy  HH");
        return this.creationDate.format(formatter);
    }
}
