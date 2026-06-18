package main.services.RegistrationCheckers;

import main.models.Section;
import main.models.Student;
import main.models.Subject;
import main.services.responses.CheckResult;
import main.services.responses.ErrorType;

public class SectionCapacityChecker implements RegistrationChecker {
    @Override
    public CheckResult check(Student student, Subject subject, Section section) {
        if (section.isFull()) {
            String message = "Section[id=" + section.getId() + "] is full";
            return CheckResult.fail(message, ErrorType.CAPACITY_FULL);
        }
        return CheckResult.success();
    }
}
