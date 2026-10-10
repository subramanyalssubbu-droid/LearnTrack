package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Student;
import java.util.ArrayList;

public class StudentRepository {
    private final ArrayList<Student> students = new ArrayList<>();
    public void save(Student student) { students.add(student); }
    public Student findById(int id) {
        for (Student student : students) if (student.getId() == id) return student;
        return null;
    }
    public ArrayList<Student> findAll() { return new ArrayList<>(students); }
}
