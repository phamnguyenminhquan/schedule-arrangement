package main.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student {
    private final String id;
    private final String name;
    private final Map<String, SubjectRecord> registeredSubjects;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.registeredSubjects = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /**
     * @deprecated
     */
    public List<SubjectRecord> getSubjectList() {
        return new ArrayList<>(registeredSubjects.values());
    }

    public boolean hasRegistered(String subjectId) {
        return registeredSubjects.containsKey(subjectId);
    }
}
