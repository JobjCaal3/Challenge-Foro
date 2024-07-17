package com.foro.api.domain.teacher;
import com.foro.api.domain.course.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("SELECT t FROM Teacher t WHERE t.user.isAccountNonLocked = true")
    Page<Teacher> listAllTeacher(Pageable pageable);
    @Query("SELECT t FROM Teacher t WHERE t.idTeacher = :idTeacher AND t.user.isAccountNonLocked = true")
    Optional<Teacher> detailTeacherActivo(Long idTeacher);
    @Query("SELECT t FROM Teacher t WHERE UPPER(t.specialty) LIKE CONCAT('%', UPPER(:specialty), '%')")
    List<Teacher> searchEspecialityTeacher(String specialty);
}
