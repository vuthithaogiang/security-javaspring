package com.giang.security.lesson;


import com.giang.security.common.BaseEntity;
import com.giang.security.course.Course;
import com.giang.security.file_storage.entity.ImageData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lesson_course")
public class Lesson extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Integer lessonId;


    @Column(name = "title_lesson", nullable = false)
    private String titleLesson;


    @Column(name = "desc_lesson")
    private String descLesson;


    @Column(name = "preview", nullable = false)
    private Boolean preview;


    @Enumerated(EnumType.STRING)
    @Column(name = "lesson_category", nullable = false)
    private LessonCategory lessonCategory;

    @OneToMany(mappedBy = "lesson")
    private List<ImageData> thumbnails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

}
