package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.Test;

import main.models.Section;
import main.models.Slot;

public class SectionTest {

    @Test
    public void testSectionConstructor_negativeId_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Section(-1);
        });
    }

    @Test
    public void testAddSlot_differentSlots() {
        Slot s1 = new Slot(DayOfWeek.MONDAY, LocalTime.of(7, 30), LocalTime.of(9, 30));
        Slot s2 = new Slot(DayOfWeek.WEDNESDAY, LocalTime.of(7, 30), LocalTime.of(9, 30));

        Section section = new Section(1)
                .addSlot(s1)
                .addSlot(s2);

        int expectedSize = 2;
        int actualSize = section.getSlots().size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testAddSlot_overlapSlots() {
        Slot s1 = new Slot(DayOfWeek.MONDAY, LocalTime.of(7, 30), LocalTime.of(9, 30));
        Slot s2 = new Slot(DayOfWeek.MONDAY, LocalTime.of(7, 30), LocalTime.of(9, 30));

        Section section = new Section(1)
                .addSlot(s1)
                .addSlot(s2);

        int expectedSize = 1;
        int actualSize = section.getSlots().size();

        assertEquals(expectedSize, actualSize);
    }
}
