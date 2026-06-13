package main.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import main.models.Subject;

public class SubjectRepo {
    private final Map<String, Subject> subjectMap;
    private static SubjectRepo instance; // singleton

    public SubjectRepo() {
        this.subjectMap = new ConcurrentHashMap<>();
    }

    public List<Subject> getSubjectList() {
        return new ArrayList<>(subjectMap.values());
    }

    public static SubjectRepo getInstance() {
        if (instance == null) {
            instance = new SubjectRepo();
        }
        return instance;
    }

    public Subject findById(String subjectId) {
        return subjectMap.get(subjectId);
    }
}
