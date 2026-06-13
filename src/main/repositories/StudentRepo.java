package main.repositories;

import java.util.ArrayList;
import java.util.List;

import main.models.Student;

public class StudentRepo {
    private final List<Student> students;
    private static StudentRepo instance; // singleton

    public StudentRepo() {
        this.students = new ArrayList<>();
    }

    public List<Student> getStudents() {
        return students;
    }

    public static StudentRepo getInstance() {
        if (instance == null) {
            instance = new StudentRepo();
        }
        return instance;
    }

    public Student findById(String studentId) {
        return this.students.stream()
                .filter((student) -> student.getId().equals(studentId))
                .findAny()
                .orElse(null);
    }
}
