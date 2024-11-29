package com.elearn.app.repositories;

import com.elearn.app.entities.Course;
import com.elearn.app.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepo extends JpaRepository<Video, String> {

    Optional<Video> findByTitle(String title);
    List<Video> findByCourse(Course course);

    List<Video> findByTitleContainingIgnoreCaseOrDescContainingIgnoreCase(String keyword, String keyword1);
}
