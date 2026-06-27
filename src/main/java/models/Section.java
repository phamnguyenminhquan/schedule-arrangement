package models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class Section {
    @NonNull
    private final String id;

    @NonNull
    private final String sectionCode;

    @NonNull
    private final String subjectId;

    private final int maxCapacity;

    @Builder.Default
    private final List<TimeSlot> timeSlots = new ArrayList<>();

    @Builder.Default
    private final Set<String> studentIds = new HashSet<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Section[id=").append(id).append("]\n");
        sb.append("- sectionCode:").append(sectionCode).append("\n");
        sb.append("- subjectId: ").append(subjectId).append("\n");
        sb.append("- maxCapacity: ").append(maxCapacity).append("\n");
        sb.append("- ").append(timeSlots).append("\n");
        sb.append("- ").append(studentIds);
        return sb.toString();
    }

    public boolean isFull() {
        return this.studentIds.size() >= this.maxCapacity;
    }

    public boolean conflictsWith(Section other) {
        return other.getTimeSlots().stream().anyMatch((otherSlot) -> {
            return this.getTimeSlots().stream().anyMatch((thisSlot) -> thisSlot.conflictsWith(otherSlot));
        });
    }

    public boolean add(String studentId) {
        return studentIds.add(studentId);
    }

    public boolean remove(String studentId) {
        return studentIds.remove(studentId);
    }
}
