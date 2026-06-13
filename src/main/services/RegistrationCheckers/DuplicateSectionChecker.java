package main.services.RegistrationCheckers;

import main.models.Section;
import main.models.Student;
import main.models.Subject;
import main.services.responses.CheckResult;
import main.services.responses.ErrorType;

public class DuplicateSectionChecker implements RegistrationChecker {
    @Override
    public CheckResult check(Student student, Subject subject, Section section) {
        if (student.hasRegistered(section.getId())) {
            String message = "Section[id=" + section.getId() + "] is already registered";
            return CheckResult.fail(message, ErrorType.DUPLICATE_SECTIONS);
        }
        return CheckResult.success();
    }
}