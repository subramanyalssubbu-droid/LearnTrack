# Design notes

## Why ArrayList instead of arrays?
An array has a fixed size. `ArrayList` grows as students, courses and enrollments are added, and supports convenient methods such as `add()` and `isEmpty()`. The three repositories each keep their own private `ArrayList` and return a copy when listing results.

## Where is static used?
`IdGenerator` has private static counters shared throughout one running program, with public static methods `getNextStudentId`, `getNextCourseId` and `getNextEnrollmentId`. `Main.main` is static because it is the JVM application entry point; menu/constants classes also declare static constants. IDs reset whenever the application restarts.

## Inheritance and polymorphism
`Student extends Person`. The `Student` constructor uses `super(...)` to initialize name/email/id fields. Both classes define `getDisplayName()`, and `Student` overrides it. A `Person person = new Student(...)` reference will call the student version, demonstrating runtime polymorphism.

## Encapsulation, overloading and separation of concerns
- Entity fields are private, with public getters and setters.
- `Student` has overloaded constructors; `StudentService.addStudent` has variants with/without email.
- Repositories hold data, services validate rules and modify entities, and `Main` only handles console menus, user input and service calls.
- `EntityNotFoundException` and `InvalidInputException` give readable errors instead of a crash.

## Decisions and limitations
- In-memory storage only; changes disappear after exiting.
- Student deactivation preserves historical enrollments. It does not automatically cancel already-active enrollments.
- Only active enrollments may transition to COMPLETED or CANCELLED; these statuses are final.
- Enrolling twice in the same course is prohibited only while there is an existing ACTIVE enrollment.
- This is an educational, single-user console program, not an authentication system or production LMS.
