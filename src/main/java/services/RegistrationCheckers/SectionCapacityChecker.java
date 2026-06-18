package services.RegistrationCheckers;

import models.Section;
import models.Student;
import models.Subject;
import services.responses.CheckResult;
import services.responses.ErrorType;

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
