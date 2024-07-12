package com.foro.api.domain.student;

import com.foro.api.domain.topic.Topic;
import com.foro.api.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "students")
@EqualsAndHashCode(of = "idStudent")
public class Student {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_student")
    private Long idStudent;
    @Column(name = "full_name")
    private String fullName;
    private String description;
    private String phone;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    private User user;

    @OneToMany(mappedBy = "student")
    private List<Topic> topics = new ArrayList<>();

    public Student(DtoRegisterStudent dtoRegisterStudent, User user) {
        this.fullName = dtoRegisterStudent.fullName();
        this.description = dtoRegisterStudent.description();
        this.phone = dtoRegisterStudent.Phone();
        this.user = user;
    }
}
