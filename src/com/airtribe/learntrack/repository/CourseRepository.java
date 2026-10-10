package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Course;
import java.util.ArrayList;

public class CourseRepository {
    private final ArrayList<Course> courses = new ArrayList<>();
    public void save(Course course) { courses.add(course); }
    public Course findById(int id) {
        for (Course course : courses) if (course.getId() == id) return course;
        return null;
    }
    public ArrayList<Course> findAll() { return new ArrayList<>(courses); }
}
