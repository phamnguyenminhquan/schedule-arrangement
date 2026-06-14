package main.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import main.models.Student;

public class StudentRepo {
    private final Map<String, Student> studentMap;

    private StudentRepo() {
        this.studentMap = new ConcurrentHashMap<>();
    }

    public List<Student> getStudentList() {
        return new ArrayList<>(studentMap.values());
    }

    // singleton
    private static class SingletonHelper {
        private static final StudentRepo INSTANCE = new StudentRepo();
    }

    public static StudentRepo getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public Student findById(String studentId) {
        return studentMap.get(studentId);
    }

    public StudentRepo add(Student student) {
        if (student != null) {
            if (!studentMap.containsKey(student.getId())) {
                studentMap.put(student.getId(), student);
            }
        }
        return this;
    }
}
