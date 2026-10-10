package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Enrollment;
import java.util.ArrayList;

public class EnrollmentRepository {
    private final ArrayList<Enrollment> enrollments = new ArrayList<>();
    public void save(Enrollment enrollment) { enrollments.add(enrollment); }
    public Enrollment findById(int id) {
        for (Enrollment enrollment : enrollments) if (enrollment.getId() == id) return enrollment;
        return null;
    }
    public ArrayList<Enrollment> findByStudentId(int studentId) {
        ArrayList<Enrollment> result = new ArrayList<>();
        for (Enrollment enrollment : enrollments)
            if (enrollment.getStudentId() == studentId) result.add(enrollment);
        return result;
    }
    public ArrayList<Enrollment> findAll() { return new ArrayList<>(enrollments); }
}
