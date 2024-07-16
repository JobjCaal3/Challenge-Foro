package com.foro.api.domain.course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    Page<Course> findAllByCurrentStatusTrue(Pageable pageable);
    @Query("SELECT c FROM Course c WHERE UPPER(c.category) LIKE CONCAT('%', UPPER(:category), '%')")
    List<Course> findByCategoryIgnoreCaseJPQL(String category);
    Optional<Course> findByidCourseAndCurrentStatusTrue(Long idCourse);
}
