package main.services.RegistrationCheckers;

import main.exceptions.SectionFullException;
import main.models.Section;
import main.models.Student;
import main.models.Subject;

public class SectionCapacityChecker implements RegistrationChecker {
    @Override
    public void check(Student student, Subject subject, Section section) {
        if (section.isFull()) {
            throw new SectionFullException("Section[id=" + section.getId() + "] is full");
        }
    }
}
