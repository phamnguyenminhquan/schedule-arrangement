package repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import models.Subject;
import services.StorageServices.StorageService;

public class SubjectRepo {
    private final Map<String, Subject> subjectMap;
    private StorageService<Subject> subjectStorage;
    private final AtomicBoolean isInitialized;

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

    public Subject findById(String subjectId) {
        return subjectMap.get(subjectId);
    }

    public void add(Subject subject) {
        if (subject != null && !subjectMap.containsKey(subject.getId())) {
            subjectMap.put(subject.getId(), subject);
        }
    }

    public SubjectRepo initialize(StorageService<Subject> subjectStorage) {
        // atomically flip from false to true, if already true => throw exception
        if (!isInitialized.compareAndSet(false, true)) {
            throw new IllegalStateException("SubjectRepo has already initialized the storage");
        }

        // guarantee non-null input
        if (subjectStorage == null) {
            isInitialized.set(false);
            throw new IllegalArgumentException("StorageService cannot be null");
        }
        this.subjectStorage = subjectStorage;
        return this;
    }

    public void load() {
        if (subjectStorage == null) {
            throw new IllegalStateException("SubjectRepo has not initialized the storage yet");
        }
        List<Subject> subjects = subjectStorage.loadData();
        for (Subject subject : subjects) {
            subjectMap.put(subject.getId(), subject);
        }
    }

    public void save() {
        if (subjectStorage == null) {
            throw new IllegalStateException("SubjectRepo has not initialized the storage yet");
        }
        subjectStorage.saveData(getSubjectList());
    }
}
