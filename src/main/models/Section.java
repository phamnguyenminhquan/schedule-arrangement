package main.models;

import java.util.ArrayList;
import java.util.List;

public class Section {
    private final String id;
    private final List<Slot> slots;
    private final int maxCapacity;
    private int capacity;

    public Section(String id, int maxCapacity) throws IllegalArgumentException {
        if (maxCapacity < 0) {
            throw new IllegalArgumentException("data cannot be negative");
        }

        this.id = id;
        this.slots = new ArrayList<>();
        this.maxCapacity = maxCapacity;
        this.capacity = 0;
    }

    public String getId() {
        return id;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return "Section [id=" + id + ", slots=" + slots + "]";
    }

    public boolean isFull() {
        return this.capacity == this.maxCapacity;
    }

    public void update() {
        this.capacity++;
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
