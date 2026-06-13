package main.services.RegistrationCheckers;

import main.models.Section;
import main.models.Student;
import main.models.Subject;

public interface RegistrationChecker {
    void check(Student student, Subject subject, Section section);
}
