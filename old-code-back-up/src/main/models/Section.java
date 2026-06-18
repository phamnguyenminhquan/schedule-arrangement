package main.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Section {
    private final String id;
    private final String sectionCode;
    private final String subjectId;
    private final int maxCapacity;
    private final List<Slot> slots;
    private final Set<String> studentIds;

    public Section(String id, String sectionCode, String subjectId, int maxCapacity) {
        this.id = id;
        this.sectionCode = sectionCode;
        this.subjectId = subjectId;
        this.maxCapacity = maxCapacity;
        this.slots = new ArrayList<>();
        this.studentIds = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public Set<String> getStudentIds() {
        return studentIds;
    }

    @Override
    public String toString() {
        return "Section [id=" + id + ", sectionCode=" + sectionCode + ", subjectId=" + subjectId + ", maxCapacity="
                + maxCapacity + ", slots=" + slots + ", studentIds=" + studentIds + "]";
    }

    public boolean isFull() {
        return this.studentIds.size() >= this.maxCapacity;
    }

    public boolean conflictsWith(Section other) {
        return other.getSlots().stream().anyMatch((otherSlot) -> {
            return this.getSlots().stream().anyMatch((thisSlot) -> thisSlot.conflictsWith(otherSlot));
        });
    }

    public Section update(String studentId) {
        this.studentIds.add(studentId);
        return this;
    }
}
