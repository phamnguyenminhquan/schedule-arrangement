package models;

import java.util.HashSet;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class Student {
    @NonNull
    private final String id;

    @NonNull
    private final String name;

    @Builder.Default
    private final Set<String> sectionIds = new HashSet<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Student[id=").append(id).append("]\n");
        sb.append("- name: ").append(name).append("\n");
        sb.append("- ").append(sectionIds);
        return sb.toString();
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