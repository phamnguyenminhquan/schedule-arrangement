package services.RegistrationCheckers;

import models.Section;
import models.Student;
import models.Subject;
import services.responses.Result;

public interface RegistrationChecker {
    Result check(Student student, Subject subject, Section section);
}
