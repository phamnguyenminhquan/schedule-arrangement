package repositories;

import models.Student;
import services.StorageServices.StorageService;
import services.responses.ErrorType;
import services.responses.Result;
import services.responses.ResultWithData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class StudentRepo {
    private final AtomicBoolean isInitialized;

    private final Map<String, Student> studentMap;
    private StorageService<Student> studentStorage;

    private StudentRepo() {
        this.studentMap = new ConcurrentHashMap<>();
        this.isInitialized = new AtomicBoolean(false);
    }

    // singleton
    private static class SingletonHelper {
        private static final StudentRepo INSTANCE = new StudentRepo();
    }

    public static StudentRepo getInstance() {
        return SingletonHelper.INSTANCE;
    }

    /**
     * Initializes the repository with the given storage service
     * 
     * @param studentStorage the storage service to use
     * @throws IllegalStateException    if the repository has already been
     *                                  initialized
     * @throws IllegalArgumentException if studentStorage is null
     */
    public void initialize(StorageService<Student> studentStorage) {
        // atomically flip from false to true, if already true => throw exception
        if (!isInitialized.compareAndSet(false, true)) {
            throw new IllegalStateException("StudentRepo has already initialized the storage");
        }
        if (studentStorage == null) {
            isInitialized.set(false);
            throw new IllegalArgumentException("StorageService cannot be null");
        }
        this.studentStorage = studentStorage;
    }

    public List<Student> getStudentList() {
        return new ArrayList<>(studentMap.values());
    }

    public Student findById(String studentId) {
        return studentMap.get(studentId);
    }

    public void add(Student student) {
        if (student != null && !studentMap.containsKey(student.getId())) {
            studentMap.put(student.getId(), student);
        }
    }

    public Result load() {
        // step 1: check if storage initialized
        if (studentStorage == null) {
            String message = "StudentRepo has not initialized the StorageService yet";
            return Result.fail(message, ErrorType.SYSTEM_ERROR);
        }

        // step 2: get result and check valid
        ResultWithData<List<Student>> storageResult = studentStorage.loadData();

        if (!storageResult.isValid()) {
            return Result.fail(storageResult.getMessage(), storageResult.getErrorType());
        }

        // step 3: update data
        List<Student> students = storageResult.getData();
        for (Student student : students) {
            studentMap.put(student.getId(), student);
        }

        return Result.success();
    }

    public Result save() {
        // step 1: check if storage initialized
        if (studentStorage == null) {
            String message = "StudentRepo has not initialized the StorageService yet";
            return Result.fail(message, ErrorType.SYSTEM_ERROR);
        }

        // step 2: return result
        return studentStorage.saveData(getStudentList());
    }
}
