package com.elearn.app.controllers;

import com.elearn.app.config.AppConstants;
import com.elearn.app.dtos.CategoryDto;
import com.elearn.app.dtos.CourseDto;
import com.elearn.app.dtos.CustomMessage;
import com.elearn.app.dtos.CustomPageResponse;
import com.elearn.app.services.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@SecurityRequirement(
//    name = "jwtScheme"
//)
@RestController
@RequestMapping("/api/v1/categories")
//@CrossOrigin(origins = "*")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CategoryDto categoryDto){

        CategoryDto createdDto = categoryService.insert(categoryDto);
        System.out.println("||||||||||");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDto);
    }

    @GetMapping
    public CustomPageResponse<CategoryDto> getAll(
            @RequestParam(value = "pageNumber", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(value = "sortBy", required = false, defaultValue = AppConstants.DEFAULT_SORT_BY) String sortBy
    ){
        return categoryService.getAll(pageNumber, pageSize, sortBy);
    }

    @GetMapping("/{categoryId}")
    public CategoryDto getSingle(@PathVariable String categoryId){
        return categoryService.get(categoryId);

    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<CustomMessage> delete(@PathVariable String categoryId){
        categoryService.delete(categoryId);
        CustomMessage customMessage = new CustomMessage();
        customMessage.setMessage("Category Deleted !!");
        customMessage.setSuccess(true);
        return ResponseEntity.status(HttpStatus.OK).body(customMessage);

    }

    @PutMapping("/{categoryId}")
    public CategoryDto update(@PathVariable String categoryId, @RequestBody CategoryDto categoryDto){
        return categoryService.update(categoryDto, categoryId);
    }

    @PostMapping("/{categoryId}/courses/{courseId}")
    public ResponseEntity<CustomMessage> addCourseToCategory(
            @PathVariable String categoryId,
            @PathVariable String courseId
    ){

        categoryService.addCourseToCategory(categoryId, courseId);
        CustomMessage customMessage = new CustomMessage();
        customMessage.setMessage("Category Updated!!");
        customMessage.setSuccess(true);

        return ResponseEntity.ok(customMessage);
    }

    @GetMapping("/{categoryId}/courses")
    public ResponseEntity<List<CourseDto>> getCoursesOfCategory(
            @PathVariable String categoryId
    ){

        return ResponseEntity.ok(categoryService.getCourseOfCat(categoryId));
    }


}
