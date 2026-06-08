package main.models;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private final String id;
    private final String name;
    private final List<SubjectRecord> records;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.records = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<SubjectRecord> getRecords() {
        return records;
    }
}
