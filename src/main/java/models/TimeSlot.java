package models;

import java.time.DayOfWeek;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class TimeSlot {
    @NonNull
    private final DayOfWeek dayOfWeek;

    @NonNull
    private final LocalTime startTime;

    @NonNull
    private final LocalTime endTime;

    @Override
    public String toString() {
        return "Slot [dayOfWeek=" + dayOfWeek + ", startTime=" + startTime + ", endTime=" + endTime + "]";
    }

    public boolean conflictsWith(TimeSlot other) {
        if (this.dayOfWeek == other.dayOfWeek) {
            return this.startTime.isBefore(other.endTime) && other.startTime.isBefore(this.endTime);
        }
        return false;
    }
}