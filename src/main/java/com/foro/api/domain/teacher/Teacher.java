package com.foro.api.domain.teacher;

import com.foro.api.domain.course.Course;
import com.foro.api.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teachers")
@EqualsAndHashCode(of = "idTeacher")
public class Teacher {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_teacher")
    private Long idTeacher;
    @Column(name = "full_name")
    private String fullName;
    @Enumerated(EnumType.STRING)
    private SpecialtyTeacher specialty;
    private String description;
    private String phone;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    private User user;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "teacher")
    private List<Course> course = new ArrayList<>();

    public Teacher(DtoRegisterTeacher dtoRegisterTeacher, User user) {
        this.fullName = dtoRegisterTeacher.fullName();
        this.specialty = dtoRegisterTeacher.specialty();
        this.description = dtoRegisterTeacher.description();
        this.phone = dtoRegisterTeacher.phone();
        this.user = user;
    }

    public void UpdateTeacher(DtoUpdateTeacher dtoUpdateTeacher) {
        if (dtoUpdateTeacher.fullName() != null){
            this.fullName = dtoUpdateTeacher.fullName();
        }
        if (dtoUpdateTeacher.specialty() != null){
            this.specialty = dtoUpdateTeacher.specialty();
        }
        if (dtoUpdateTeacher.description() != null){
            this.description = dtoUpdateTeacher.description();
        }
        if (dtoUpdateTeacher.phone() != null){
            this.phone = dtoUpdateTeacher.phone();
        }
    }
}
