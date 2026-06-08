package main.models;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private final String id;
    private final String name;
    private final List<Section> sections;

    public Subject(String id, String name) {
        this.id = id;
        this.name = name;
        this.sections = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Section> getSections() {
        return sections;
    }

    @Override
    public String toString() {
        return "Subject [id=" + id + ", name=" + name + ", sections=" + sections + "]";
    }
}
