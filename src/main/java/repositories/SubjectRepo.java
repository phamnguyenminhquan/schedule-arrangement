package repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import models.Subject;
import services.StorageServices.StorageService;
import services.responses.ErrorType;
import services.responses.Result;
import services.responses.ResultWithData;

public class SubjectRepo {
    private final AtomicBoolean isInitialized;

    private final Map<String, Subject> subjectMap;
    private StorageService<Subject> subjectStorage;

    private SubjectRepo() {
        this.subjectMap = new ConcurrentHashMap<>();
        this.isInitialized = new AtomicBoolean(false);
    }

    public List<Subject> getSubjectList() {
        return new ArrayList<>(subjectMap.values());
    }

    // singleton
    private static class SingletonHelper {
        private static final SubjectRepo INSTANCE = new SubjectRepo();
    }

    public static SubjectRepo getInstance() {
        return SingletonHelper.INSTANCE;
    }

    /**
     * Initializes the repository with the given storage service
     * 
     * @param subjectStorage the storage service to use
     * @throws IllegalStateException    if the repository has already been
     *                                  initialized
     * @throws IllegalArgumentException if subjectStorage is null
     */
    public void initialize(StorageService<Subject> subjectStorage) {
        // atomically flip from false to true, if already true => throw exception
        if (!isInitialized.compareAndSet(false, true)) {
            throw new IllegalStateException("SubjectRepo has already initialized the storage");
        }
        if (subjectStorage == null) {
            isInitialized.set(false);
            throw new IllegalArgumentException("StorageService cannot be null");
        }
        this.subjectStorage = subjectStorage;
    }

    public Subject findById(String subjectId) {
        return subjectMap.get(subjectId);
    }

    public void add(Subject subject) {
        if (subject != null && !subjectMap.containsKey(subject.getId())) {
            subjectMap.put(subject.getId(), subject);
        }
    }

    public Result load() {
        // step 1: check if storage initialized
        if (subjectStorage == null) {
            String message = "SubjectRepo has not initialized the StorageService yet";
            return Result.fail(message, ErrorType.SYSTEM_ERROR);
        }

        // step 2: get result and check valid
        ResultWithData<List<Subject>> storageResult = subjectStorage.loadData();

        if (!storageResult.isValid()) {
            return Result.fail(storageResult.getMessage(), storageResult.getErrorType());
        }

        // step 3: update data
        List<Subject> subjects = storageResult.getData();
        for (Subject subject : subjects) {
            subjectMap.put(subject.getId(), subject);
        }

        return Result.success();
    }

    public Result save() {
        // step 1: check if storage initialized
        if (subjectStorage == null) {
            String message = "SubjectRepo has not initialized the StorageService yet";
            return Result.fail(message, ErrorType.SYSTEM_ERROR);
        }

        // step 2: return result
        return subjectStorage.saveData(getSubjectList());
    }
}
