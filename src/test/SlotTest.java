package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.Test;

import main.models.Slot;

public class SlotTest {

    @Test
    public void testSlotConstructor_nullArguments_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Slot(null, null, null);
        });
    }

    @Test
    public void testSlotConstructor_startTimeAfterEndTime_shouldThrowException() {
        // startTime = 9h30
        LocalTime starTime = LocalTime.of(9, 30);
        // endTime = 7h30
        LocalTime endTime = LocalTime.of(7, 30);

        assertThrows(IllegalArgumentException.class, () -> {
            new Slot(DayOfWeek.FRIDAY, starTime, endTime);
        });
    }

    @Test
    public void testSlotsConflict_partlyOverlap_shouldReturnTrue() {
        // Slot 1: Mon 7h30 - 9h30
        Slot s1 = new Slot(DayOfWeek.MONDAY, LocalTime.of(7, 30), LocalTime.of(9, 30));
        // Slot 2: Mon 8h30 - 10h30
        Slot s2 = new Slot(DayOfWeek.MONDAY, LocalTime.of(8, 30), LocalTime.of(10, 30));

        assertTrue(s1.conflictsWith(s2));
    }

    @Test
    public void testSlotsConflict_fullyOverlap_shouldReturnTrue() {
        // Slot 1: Mon 7h30 - 9h30
        Slot s1 = new Slot(DayOfWeek.MONDAY, LocalTime.of(7, 30), LocalTime.of(9, 30));
        // Slot 2: Mon 7h30 - 10h30
        Slot s2 = new Slot(DayOfWeek.MONDAY, LocalTime.of(7, 30), LocalTime.of(9, 30));

        assertTrue(s1.conflictsWith(s2));
    }

    @Test
    public void testSlotsDoNotConflict_differentDays_shouldReturnFalse() {
        // Slot 1: Mon 7h30 = 9h30
        Slot s1 = new Slot(DayOfWeek.MONDAY, LocalTime.of(7, 30), LocalTime.of(9, 30));
        // Slot 2: Tue 7h30 - 9h30
        Slot s2 = new Slot(DayOfWeek.TUESDAY, LocalTime.of(7, 30), LocalTime.of(9, 30));

        assertFalse(s1.conflictsWith(s2));
    }

    @Test
    public void testSlotsDoNotConflict_consecutiveSlots_shouldReturnFalse() {
        // Slot 1: Mon 7h30 = 9h30
        Slot s1 = new Slot(DayOfWeek.MONDAY, LocalTime.of(7, 30), LocalTime.of(9, 30));
        // Slot 2: Mon 9h30 - 11h30
        Slot s2 = new Slot(DayOfWeek.MONDAY, LocalTime.of(9, 30), LocalTime.of(11, 30));

        assertFalse(s1.conflictsWith(s2));
    }
}