package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;
import java.util.ArrayList;

public class StudentService {
    private final StudentRepository repository;
    public StudentService(StudentRepository repository) { this.repository = repository; }

    public Student addStudent(String firstName, String lastName, String email, String batch) {
        String first = InputValidator.requireText(firstName, "First name");
        String last = InputValidator.requireText(lastName, "Last name");
        String validatedBatch = InputValidator.requireText(batch, "Batch");
        String validatedEmail = InputValidator.optionalEmail(email);
        Student student = new Student(IdGenerator.getNextStudentId(), first, last, validatedEmail, validatedBatch);
        repository.save(student);
        return student;
    }

    // Method overloading: variant without email.
    public Student addStudent(String firstName, String lastName, String batch) {
        return addStudent(firstName, lastName, "", batch);
    }

    public Student findStudentById(int id) {
        Student student = repository.findById(id);
        if (student == null) throw new EntityNotFoundException("Student ID " + id + " not found.");
        return student;
    }
    public ArrayList<Student> listStudents() { return repository.findAll(); }
    public void deactivateStudent(int id) { findStudentById(id).setActive(false); }
    public void updateStudent(int id, String firstName, String lastName, String email, String batch) {
        String first = InputValidator.requireText(firstName, "First name");
        String last = InputValidator.requireText(lastName, "Last name");
        String validatedEmail = InputValidator.optionalEmail(email);
        String validatedBatch = InputValidator.requireText(batch, "Batch");
        Student student = findStudentById(id);
        student.setFirstName(first);
        student.setLastName(last);
        student.setEmail(validatedEmail);
        student.setBatch(validatedBatch);
    }
    // Soft removal preserves historical enrollments.
    public void removeStudent(int id) { deactivateStudent(id); }
}
