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
        return "Section [id=" + id + ", slots=" + slots + "]";
    }

    public boolean conflictsWith(Section other) {
        if (other.slots.isEmpty() || this.slots.isEmpty()) {
            return false;
        }
        // Hiểu là: nếu có bất kì slot nào của other mà ...
        return other.slots.stream().anyMatch((otherSlot) -> {
            // ... nó lại xung đột với bất kì slot nào của this thì return true
            return this.slots.stream().anyMatch((currentSlot) -> currentSlot.conflictsWith(otherSlot));
        });
    }
}
