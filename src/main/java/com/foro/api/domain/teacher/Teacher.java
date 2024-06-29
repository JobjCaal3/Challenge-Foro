package com.foro.api.domain.teacher;

import com.foro.api.domain.course.Course;
import com.foro.api.domain.role.Role;
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
    private String specialty;
    private int age;
    private String phone;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    private User user;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id_role")
    private Role role;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "teacher")
    private List<Course> course = new ArrayList<>();
}
