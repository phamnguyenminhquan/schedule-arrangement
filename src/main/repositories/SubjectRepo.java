package main.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import main.models.Subject;

public class SubjectRepo {
    private final Map<String, Subject> subjectMap;

    private SubjectRepo() {
        this.subjectMap = new ConcurrentHashMap<>();
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
}
