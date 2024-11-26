package com.elearn.app.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Video {

    @Id
    private String videoId;

    @Column(nullable = true)
    private String title;

    @Column(name = "description")
    private String desc;

    private String filePath;

    private String contentType;

    @ManyToOne
    private Course course;
}
