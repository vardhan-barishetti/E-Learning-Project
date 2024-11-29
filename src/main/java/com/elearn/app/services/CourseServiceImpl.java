package com.elearn.app.services;

import com.elearn.app.dtos.CourseDto;
import com.elearn.app.entities.Course;
import com.elearn.app.exceptions.ResourceNotFoundException;
import com.elearn.app.repositories.CourseRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService{

    private CourseRepo courseRepo;

    private ModelMapper modelMapper;

    public CourseServiceImpl(CourseRepo courseRepo, ModelMapper modelMapper) {
        this.courseRepo = courseRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public CourseDto createCourse(CourseDto courseDto) {

        courseDto.setId((UUID.randomUUID()).toString());
        courseDto.setCreatedDate(new Date());

        Course course = modelMapper.map(courseDto, Course.class);

        Course savedCourse = courseRepo.save(course);

        return modelMapper.map(savedCourse, CourseDto.class);
    }

    @Override
    public CourseDto updateCourse(String id, CourseDto courseDto) {
        Course course = courseRepo.findById(id).orElseThrow(()-> new RuntimeException("Course not found"));
        course.setTitle(courseDto.getTitle());
        course.setLongDesc(courseDto.getLongDesc());
        course.setShortDesc(courseDto.getShortDesc());
        course.setPrice(courseDto.getPrice());
        course.setDiscount(courseDto.getDiscount());

        Course updatedCourse = courseRepo.save(course);

        CourseDto courseDto1 = modelMapper.map(updatedCourse, CourseDto.class);

        return courseDto1;
    }

    @Override
    public CourseDto getCourseById(String id) {
        Course course = courseRepo.findById(id).orElseThrow(()-> new RuntimeException("Course not found"));

        return modelMapper.map(course, CourseDto.class);
    }

    @Override
    public Page<CourseDto> getAllCourses(Pageable pageable) {
        Page<Course> courses = courseRepo.findAll(pageable);
        List<CourseDto> dtos = courses.getContent().stream().map((course)-> modelMapper.map(course, CourseDto.class)).collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, courses.getTotalElements());
    }

    @Override
    public void deleteCourse(String courseId) {
        Course course = courseRepo.findById(courseId).orElseThrow(()->new ResourceNotFoundException("Course Not found"));
        courseRepo.delete(course);

    }

    @Override
    public List<CourseDto> searchCourses(String keyword) {

        List<Course> courses = courseRepo.findByTitleContainingIgnoreCaseOrShortDescContainingIgnoreCase(keyword, keyword);
        return courses.stream()
                .map(course -> modelMapper.map(course, CourseDto.class))
                .collect(Collectors.toList());
    }


    public CourseDto entityToDto(Course course){

//        CourseDto courseDto = new CourseDto();
//
//        courseDto.setId(course.getId());
//        courseDto.setTitle(course.getTitle());
//        courseDto.setShortDesc(course.getShortDesc());
//        //do rest of the fields

        CourseDto courseDto =  modelMapper.map(course, CourseDto.class);
        return courseDto;
    }

    public Course dtotoEntity(CourseDto dto){
//        Course course = new Course();
//        course.setId(dto.getId());
//        //do rest of the fields

        Course course =  modelMapper.map(dto, Course.class);
        return course;
    }
}
