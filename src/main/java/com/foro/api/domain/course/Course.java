package com.foro.api.domain.course;

import com.foro.api.domain.teacher.Teacher;
import com.foro.api.domain.topic.Topic;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    @Enumerated(EnumType.STRING)
    private CategoryCourse category;
    @Column(name = "description_course")
    private String descriptionCourse;
    @Column(name = "current_status")
    private Boolean currentStatus;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "teacher_id", referencedColumnName = "id_teacher")
    private Teacher teacher;

    @OneToMany(mappedBy = "course")
    private List<Topic> topics = new ArrayList<>();

    public Course(DtoCreateCourse dtoCreateCourse, Teacher teacher) {
        this.name = dtoCreateCourse.name();
        this.category = dtoCreateCourse.category();
        this.descriptionCourse = dtoCreateCourse.descriptionCourse();
        this.currentStatus = true;
        this.teacher = teacher;
    }

    public void updateCourse(DtoUpdateCourse dtoUpdateCourse, Teacher teacher){
        if(dtoUpdateCourse.name() != null) this.name = dtoUpdateCourse.name();
        if(dtoUpdateCourse.category() != null) this.category = dtoUpdateCourse.category();
        if(dtoUpdateCourse.descriptionCourse() != null) this.descriptionCourse = dtoUpdateCourse.descriptionCourse();
        if(teacher != null) this.teacher = teacher;
    }

    public void deltedCourse(Long idCourse) {
        this.currentStatus = false;
    }
}