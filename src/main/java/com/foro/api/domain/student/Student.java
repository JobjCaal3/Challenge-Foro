package com.foro.api.domain.student;

import com.foro.api.domain.role.Role;
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
    private int age;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    private User user;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id_role")
    private Role role;

    @OneToMany(mappedBy = "student")
    private List<Topic> topics = new ArrayList<>();

}
