package main.repositories;

import java.util.ArrayList;
import java.util.List;

import main.models.Subject;

public class SubjectRepo {
    private final List<Subject> subjects;
    private static SubjectRepo instance; // singleton

    public SubjectRepo() {
        this.subjects = new ArrayList<>();
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public static SubjectRepo getInstance() {
        if (instance == null) {
            instance = new SubjectRepo();
        }
        return instance;
    }

    public Subject findById(String subjectId) {
        return this.subjects.stream()
                .filter((subject) -> subject.getId().equals(subjectId))
                .findAny()
                .orElse(null);
    }
}
