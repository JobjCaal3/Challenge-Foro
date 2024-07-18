package com.foro.api.domain.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    @Query("SELECT s FROM Student s WHERE s.user.isAccountNonLocked = true")
    Page<Student> findAllStudentAsset(Pageable pageable);
    @Query("SELECT s FROM Student s WHERE s.idStudent = :idStudent AND s.user.isAccountNonLocked = true")
    Optional<Student> searchStudentActive(Long idStudent);
}
