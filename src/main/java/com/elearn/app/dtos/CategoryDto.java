package com.elearn.app.dtos;

import com.elearn.app.entities.Course;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryDto {

    private String id;

    @NotEmpty(message = "Title is required")
    @Size(min = 3, max = 50, message = "Title must be between 3 and 5 characters")
    private String title;

    @NotEmpty(message = "Description is required")
    private String desc;

    private Date addedDate;

}
