package com.foro.api.domain.user;

import com.foro.api.domain.answer.Answer;
import com.foro.api.domain.student.Student;
import com.foro.api.domain.teacher.Teacher;
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
@Table(name = "users")
@EqualsAndHashCode(of = "idUser")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;
    private String email;
    private String password;
    @Column(name = "is_account_non_locked")
    private Boolean isAccountNonLocked;

    @OneToOne(mappedBy = "user")
    private Student student;

    @OneToOne(mappedBy = "user")
    private Teacher teacher;

    @OneToMany(mappedBy = "user")
    private List<Answer> answers = new ArrayList<>();


}
