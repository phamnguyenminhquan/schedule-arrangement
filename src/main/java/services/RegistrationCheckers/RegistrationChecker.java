package services.RegistrationCheckers;

import models.Section;
import models.Student;
import models.Subject;
import services.responses.CheckResult;

public interface RegistrationChecker {
    CheckResult check(Student student, Subject subject, Section section);
}
