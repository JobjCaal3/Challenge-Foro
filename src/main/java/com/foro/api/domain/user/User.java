package com.foro.api.domain.user;

import com.foro.api.domain.Answer.Answer;
import com.foro.api.domain.role.Role;
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
    @Column(unique = true)
    private String email;
    private String password;
    @Column(name = "is_account_non_locked")
    private Boolean isAccountNonLocked;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id_role")
    private Role role;

    @OneToOne(mappedBy = "user")
    private Student student;

    @OneToOne(mappedBy = "user")
    private Teacher teacher;

    @OneToMany(mappedBy = "user")
    private List<Answer> answers = new ArrayList<>();

    public User(DtoRegisterUser dtoRegisterUser , Role role){
        this.email = dtoRegisterUser.email();
        this.isAccountNonLocked = true;
        this.role = role;
    }

    public User updatePassword(DtoUpdateUser user) {
            this.password = user.password();
            return this;
    }

    public void deletedUser() {
        this.isAccountNonLocked = false;
    }
}
