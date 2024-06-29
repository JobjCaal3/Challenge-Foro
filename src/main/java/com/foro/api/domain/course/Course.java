package com.foro.api.domain.course;

import com.foro.api.domain.teacher.Teacher;
import com.foro.api.domain.topic.Topic;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses")
@EqualsAndHashCode(of = "idCourse")
public class Course {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_course")
    private Long idCourse;
    private String name;
    private String category;
    @Column(name = "current_status")
    private Boolean currentStatus;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "teacher_id", referencedColumnName = "id_teacher")
    private Teacher teacher;

    @OneToMany(mappedBy = "course")
    private List<Topic> topics = new ArrayList<>();

}