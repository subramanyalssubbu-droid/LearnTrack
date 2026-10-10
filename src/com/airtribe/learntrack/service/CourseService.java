package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;
import java.util.ArrayList;

public class CourseService {
    private final CourseRepository repository;
    public CourseService(CourseRepository repository) { this.repository = repository; }

    public Course addCourse(String name, String description, int durationInWeeks) {
        String validatedName = InputValidator.requireText(name, "Course name");
        String validatedDescription = InputValidator.requireText(description, "Description");
        InputValidator.requirePositive(durationInWeeks, "Duration");
        Course course = new Course(IdGenerator.getNextCourseId(), validatedName, validatedDescription, durationInWeeks);
        repository.save(course);
        return course;
    }
    public Course findCourseById(int id) {
        Course course = repository.findById(id);
        if (course == null) throw new EntityNotFoundException("Course ID " + id + " not found.");
        return course;
    }
    public ArrayList<Course> listCourses() { return repository.findAll(); }
    public void setCourseActive(int id, boolean active) { findCourseById(id).setActive(active); }
}
