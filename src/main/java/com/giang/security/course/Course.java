package com.giang.security.course;

import com.giang.security.common.BaseEntity;
import com.giang.security.file_storage.entity.ImageData;
import com.giang.security.lesson.Lesson;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "course")
public class Course extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  courseId;

    @Column(name = "name_course", nullable = false)
    private String nameCourse;


    @Column(name = "overview_content", nullable = false)
    private String overviewContent;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_course", nullable = false)
    private CategoryCourse categoryCourse;

    @Enumerated(EnumType.STRING)
    @Column(name = " level", nullable = false)
    private Level level;


    @OneToMany(mappedBy = "course")
    private List<Lesson> lessons;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banner_img")
    private ImageData imageData;

}
