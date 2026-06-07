package main.models;

import java.util.ArrayList;
import java.util.List;

public class Section {
    private final int id;
    private final List<Slot> slots;

    public Section(int id) throws IllegalArgumentException {
        if (id < 0) {
            throw new IllegalArgumentException("id cannot be negative");
        }

        this.id = id;
        this.slots = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Section [").append("id=").append(id).append(", slots=[\n");
        for (int i = 0; i < slots.size(); i++) {
            sb.append(slots.get(i)).append(",\n");
        }
        sb.append("]]");
        return sb.toString();
    }

    public Section addSlot(Slot newSlot) {
        if (slots.stream().noneMatch((slot) -> slot.conflictsWith(newSlot))) {
            slots.add(newSlot);
        }
        return this;
    }
}
