package com.airtribe.learntrack;

import com.airtribe.learntrack.constants.AppConstants;
import com.airtribe.learntrack.constants.MenuOptions;
import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.StudentService;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final StudentService students = new StudentService(new StudentRepository());
    private final CourseService courses = new CourseService(new CourseRepository());
    private final EnrollmentService enrollments =
            new EnrollmentService(new EnrollmentRepository(), students, courses);

    public static void main(String[] args) { new Main().run(); }

    private void run() {
        System.out.println("Welcome to " + AppConstants.APP_NAME);
        boolean running = true;
        while (running) {
            System.out.println("\nMAIN MENU: 1. Students  2. Courses  3. Enrollments  0. Exit");
            try {
                int choice = readInt("Choose: ");
                switch (choice) {
                    case MenuOptions.STUDENTS: studentMenu(); break;
                    case MenuOptions.COURSES: courseMenu(); break;
                    case MenuOptions.ENROLLMENTS: enrollmentMenu(); break;
                    case MenuOptions.EXIT: running = false; break;
                    default: System.out.println("Unknown option. Please try again.");
                }
            } catch (InvalidInputException | EntityNotFoundException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
        System.out.println("Goodbye!");
    }

    private void studentMenu() {
        System.out.println("\nSTUDENTS: 1. Add  2. List  3. Search  4. Update  5. Deactivate  0. Back");
        switch (readInt("Choose: ")) {
            case 1:
                String first = readText("First name: ");
                String last = readText("Last name: ");
                String email = readText("Email (optional): ");
                String batch = readText("Batch: ");
                System.out.println("Created: " + students.addStudent(first, last, email, batch));
                break;
            case 2: printStudents(students.listStudents()); break;
            case 3: System.out.println(students.findStudentById(readInt("Student ID: "))); break;
            case 4:
                int id = readInt("Student ID: ");
                students.updateStudent(id, readText("New first name: "), readText("New last name: "),
                        readText("New email (optional): "), readText("New batch: "));
                System.out.println("Student updated."); break;
            case 5: students.deactivateStudent(readInt("Student ID: "));
                System.out.println("Student deactivated."); break;
            case 0: break;
            default: System.out.println("Unknown student option.");
        }
    }

    private void courseMenu() {
        System.out.println("\nCOURSES: 1. Add  2. List  3. Activate  4. Deactivate  0. Back");
        switch (readInt("Choose: ")) {
            case 1:
                System.out.println("Created: " + courses.addCourse(readText("Course name: "),
                        readText("Description: "), readInt("Duration (weeks): ")));
                break;
            case 2:
                ArrayList<Course> all = courses.listCourses();
                if (all.isEmpty()) System.out.println("No courses yet.");
                else for (Course course : all) System.out.println(course);
                break;
            case 3: courses.setCourseActive(readInt("Course ID: "), true);
                System.out.println("Course activated."); break;
            case 4: courses.setCourseActive(readInt("Course ID: "), false);
                System.out.println("Course deactivated."); break;
            case 0: break;
            default: System.out.println("Unknown course option.");
        }
    }

    private void enrollmentMenu() {
        System.out.println("\nENROLLMENTS: 1. Enroll  2. View by student  3. Complete  4. Cancel  0. Back");
        switch (readInt("Choose: ")) {
            case 1:
                System.out.println("Created: " + enrollments.enrollStudent(
                        readInt("Student ID: "), readInt("Course ID: ")));
                break;
            case 2:
                ArrayList<Enrollment> all = enrollments.listEnrollmentsForStudent(readInt("Student ID: "));
                if (all.isEmpty()) System.out.println("No enrollments for this student.");
                else for (Enrollment enrollment : all) System.out.println(enrollment);
                break;
            case 3: enrollments.changeStatus(readInt("Enrollment ID: "), EnrollmentStatus.COMPLETED);
                System.out.println("Enrollment completed."); break;
            case 4: enrollments.changeStatus(readInt("Enrollment ID: "), EnrollmentStatus.CANCELLED);
                System.out.println("Enrollment cancelled."); break;
            case 0: break;
            default: System.out.println("Unknown enrollment option.");
        }
    }

    private void printStudents(ArrayList<Student> all) {
        if (all.isEmpty()) System.out.println("No students yet.");
        else for (Student student : all) System.out.println(student);
    }

    private String readText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private int readInt(String prompt) {
        String value = readText(prompt);
        try { return Integer.parseInt(value.trim()); }
        catch (NumberFormatException ex) { throw new InvalidInputException("Enter a whole number."); }
    }
}
