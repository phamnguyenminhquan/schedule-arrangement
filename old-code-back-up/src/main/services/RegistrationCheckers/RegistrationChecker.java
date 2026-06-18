package main.services.RegistrationCheckers;

import main.models.Section;
import main.models.Student;
import main.models.Subject;
import main.services.responses.CheckResult;

public interface RegistrationChecker {
    CheckResult check(Student student, Subject subject, Section section);
}
