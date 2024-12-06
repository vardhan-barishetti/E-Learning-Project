package com.elearn.app.services;

import com.elearn.app.config.AppConstants;
import com.elearn.app.dtos.CourseDto;
import com.elearn.app.dtos.CustomPageResponse;
import com.elearn.app.dtos.ResourceContentType;
import com.elearn.app.entities.Category;
import com.elearn.app.entities.Course;
import com.elearn.app.exceptions.ResourceNotFoundException;
import com.elearn.app.repositories.CourseRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService{

    private CourseRepo courseRepo;

    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

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
        System.out.println("|||||||||||");

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
    public CustomPageResponse<CourseDto> getAllCourses(int pageNumber, int pageSize, String sortBy) {
        Sort sort = Sort.by(sortBy).ascending();
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page<Course> coursePage = courseRepo.findAll(pageRequest);


        List<CourseDto> dtos = coursePage.getContent().stream().map((course)-> modelMapper.map(course, CourseDto.class)).collect(Collectors.toList());

        CustomPageResponse<CourseDto> response = new CustomPageResponse<>();
        response.setContent(dtos);

        return response;
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

    @Override
    public CourseDto saveBanner(MultipartFile file, String courseId) throws IOException {

        Course course = courseRepo.findById(courseId).orElseThrow(()->new ResourceNotFoundException("Course Not found"));

        String filePath = fileService.save(file, AppConstants.COURSE_BANNER_UPLOAD_DIR, file.getOriginalFilename());
        course.setBannerName(filePath);
        course.setBannerContentType(file.getContentType());
        Course savedCourse = courseRepo.save(course);

        return modelMapper.map(savedCourse, CourseDto.class);
    }

    @Override
    public ResourceContentType getCourseBannerById(String courseId) {
        Course course = courseRepo.findById(courseId).orElseThrow(()->new ResourceNotFoundException("Course Not found"));

        String bannerPath = course.getBannerName();
        Path path = Paths.get(bannerPath);
        Resource resource = new FileSystemResource(path);
        ResourceContentType resourceContentType = new ResourceContentType();
        resourceContentType.setResource(resource);
        resourceContentType.setContentType(course.getBannerContentType());

        return resourceContentType;
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