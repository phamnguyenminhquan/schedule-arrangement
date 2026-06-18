package services.RegistrationCheckers;

import models.Section;
import models.Student;
import models.Subject;
import services.responses.CheckResult;
import services.responses.ErrorType;

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