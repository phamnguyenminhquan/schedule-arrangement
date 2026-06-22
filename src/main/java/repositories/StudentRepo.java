package repositories;

import models.Student;
import services.StorageServices.StorageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class StudentRepo {
    private final Map<String, Student> studentMap;
    private StorageService<Student> studentStorage;
    private final AtomicBoolean isInitialized;

    private StudentRepo() {
        this.studentMap = new ConcurrentHashMap<>();
        this.isInitialized = new AtomicBoolean(false);
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

    public void add(Student student) {
        if (student != null && !studentMap.containsKey(student.getId())) {
            studentMap.put(student.getId(), student);
        }
    }

    public StudentRepo initialize(StorageService<Student> studentStorage) {
        // atomically flip from false to true, if already true => throw exception
        if (!isInitialized.compareAndSet(false, true)) {
            throw new IllegalStateException("StudentRepo has already initialized the storage");
        }

        // guarantee non-null input
        if (studentStorage == null) {
            isInitialized.set(false);
            throw new IllegalArgumentException("StorageService cannot be null");
        }
        this.studentStorage = studentStorage;
        return this;
    }

    public void load() {
        if (studentStorage == null) {
            throw new IllegalStateException("StudentRepo has not initialized the storage yet");
        }
        List<Student> students = studentStorage.loadData();
        for (Student student : students) {
            studentMap.put(student.getId(), student);
        }
    }

    public void save() {
        if (studentStorage == null) {
            throw new IllegalStateException("StudentRepo has not initialized the storage yet");
        }
        studentStorage.saveData(getStudentList());
    }
}
