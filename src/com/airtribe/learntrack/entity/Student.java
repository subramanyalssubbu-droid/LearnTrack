package com.airtribe.learntrack.entity;

public class Student extends Person {
    private String batch;
    private boolean active;

    public Student(int id, String firstName, String lastName, String email, String batch) {
        super(id, firstName, lastName, email);
        this.batch = batch;
        this.active = true;
    }

    // Constructor overloading: email is optional.
    public Student(int id, String firstName, String lastName, String batch) {
        this(id, firstName, lastName, "", batch);
    }

    public String getBatch() { return batch; }
    public void setBatch(String batch) { this.batch = batch; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public String getDisplayName() { return "Student: " + super.getDisplayName(); }

    @Override
    public String toString() {
        return "ID: " + getId() + " | " + getDisplayName() + " | Email: "
                + (getEmail().isEmpty() ? "N/A" : getEmail()) + " | Batch: " + batch
                + " | " + (active ? "ACTIVE" : "INACTIVE");
    }
}
