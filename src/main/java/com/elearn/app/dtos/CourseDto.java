package com.elearn.app.dtos;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourseDto {

        private String id;
        private String title;
        private String shortDesc;

        private String longDesc;
        private double price;
        private boolean live=false;
        private double discount;
        private Date createdDate;

        private String bannerName;

        //videos
        private List<VideoDto> videos = new ArrayList<>();

        //category

        private List<CategoryDto> categoryList = new ArrayList<>();

}
