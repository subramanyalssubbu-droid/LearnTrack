package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.util.IdGenerator;
import java.time.LocalDate;
import java.util.ArrayList;

public class EnrollmentService {
    private final EnrollmentRepository repository;
    private final StudentService studentService;
    private final CourseService courseService;
    public EnrollmentService(EnrollmentRepository repository, StudentService students, CourseService courses) {
        this.repository = repository;
        this.studentService = students;
        this.courseService = courses;
    }

    public Enrollment enrollStudent(int studentId, int courseId) {
        Student student = studentService.findStudentById(studentId);
        Course course = courseService.findCourseById(courseId);
        if (!student.isActive()) throw new InvalidInputException("Inactive students cannot enroll.");
        if (!course.isActive()) throw new InvalidInputException("Cannot enroll in an inactive course.");
        for (Enrollment existing : repository.findByStudentId(studentId)) {
            if (existing.getCourseId() == courseId && existing.getStatus() == EnrollmentStatus.ACTIVE)
                throw new InvalidInputException("This student already has an active enrollment in that course.");
        }
        Enrollment enrollment = new Enrollment(IdGenerator.getNextEnrollmentId(), studentId, courseId, LocalDate.now());
        repository.save(enrollment);
        return enrollment;
    }

    public ArrayList<Enrollment> listEnrollmentsForStudent(int studentId) {
        studentService.findStudentById(studentId);
        return repository.findByStudentId(studentId);
    }
    public Enrollment findEnrollmentById(int id) {
        Enrollment enrollment = repository.findById(id);
        if (enrollment == null) throw new EntityNotFoundException("Enrollment ID " + id + " not found.");
        return enrollment;
    }
    public void changeStatus(int id, EnrollmentStatus newStatus) {
        if (newStatus == null) throw new InvalidInputException("Enrollment status cannot be null.");
        Enrollment enrollment = findEnrollmentById(id);
        if (newStatus == EnrollmentStatus.ACTIVE)
            throw new InvalidInputException("Use enrollStudent to create an active enrollment.");
        if (enrollment.getStatus() != EnrollmentStatus.ACTIVE)
            throw new InvalidInputException("Only ACTIVE enrollments may be completed or cancelled.");
        enrollment.setStatus(newStatus);
    }
}
