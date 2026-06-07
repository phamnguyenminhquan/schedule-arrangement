package main.models;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Slot {
    // final variables to guarantee immutability
    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public Slot(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        // Validate input
        if (dayOfWeek == null || startTime == null || endTime == null) {
            throw new IllegalArgumentException("datas cannot be null");
        }
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("startTime must be before endTime");
        }

        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "Slot [dayOfWeek=" + dayOfWeek + ", startTime=" + startTime + ", endTime=" + endTime + "]";
    }

    // check if 2 slot is overlap (conflict with each other)
    public boolean conflictsWith(Slot other) {
        if (this.dayOfWeek == other.dayOfWeek) {
            if (this.startTime.isBefore(other.endTime) && other.startTime.isBefore(this.endTime)) {
                return true;
            }
        }
        return false;
    }
}