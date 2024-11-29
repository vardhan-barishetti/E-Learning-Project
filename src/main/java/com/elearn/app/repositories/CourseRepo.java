package com.elearn.app.repositories;

import com.elearn.app.entities.Category;
import com.elearn.app.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepo extends JpaRepository<Course, String> {

    Optional<Course> findByTitle(String title);
    List<Course> findByLive(boolean live);

    List<Course> findByTitleContainingIgnoreCaseOrShortDescContainingIgnoreCase(String keyword, String keyword1);

    //search the course by title
}
