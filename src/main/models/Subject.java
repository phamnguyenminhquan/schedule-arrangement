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
        StringBuilder sb = new StringBuilder();
        sb.append("Subject{id=").append(id).append(", name=").append(name).append(", sections=[\n");

        for (Section section : sections) {
            sb.append("\t\t").append(section).append(",\n");
        }

        sb.append("\t]\n}");
        return sb.toString();
    }

    // Subject can have multiple sections conflicting with each other.
    public Subject addSection(Section newSection) {
        sections.add(newSection);
        return this;
    }
}
