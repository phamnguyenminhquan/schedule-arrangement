package main.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import main.models.Student;

public class StudentRepo {
    private final Map<String, Student> studentMap;
    private static StudentRepo instance; // singleton

    public StudentRepo() {
        this.studentMap = new ConcurrentHashMap<>();
    }

    public List<Student> getStudentList() {
        return new ArrayList<>(studentMap.values());
    }

    public static StudentRepo getInstance() {
        if (instance == null) {
            instance = new StudentRepo();
        }
        return instance;
    }

    public Student findById(String studentId) {
        return studentMap.get(studentId);
    }
}
