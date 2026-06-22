package models;

import java.util.HashSet;
import java.util.Set;

public class Student {
    private final String id;
    private final String name;
    private final Set<String> sectionIds;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.sectionIds = new HashSet<>();
    }

    // use for json
    public Student(String id, String name, Set<String> sectionIds) {
        this.id = id;
        this.name = name;
        this.sectionIds = sectionIds;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<String> getSectionIds() {
        return sectionIds;
    }

    @Override
    public String toString() {
        return "Student [id=" + id + ", name=" + name + ", sectionIds=" + sectionIds + "]";
    }

    public boolean hasRegistered(String sectionId) {
        return this.sectionIds.contains(sectionId);
    }

    public boolean add(String sectionId) {
        return sectionIds.add(sectionId);
    }

    public boolean remove(String sectionId) {
        return sectionIds.remove(sectionId);
    }
}