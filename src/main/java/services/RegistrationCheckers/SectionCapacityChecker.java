package services.RegistrationCheckers;

import models.Section;
import models.Student;
import models.Subject;
import services.responses.Result;
import services.responses.ErrorType;

public class SectionCapacityChecker implements RegistrationChecker {
    @Override
    public Result check(Student student, Subject subject, Section section) {
        if (section.isFull()) {
            String message = "Section[id=" + section.getId() + "] is full";
            return Result.fail(message, ErrorType.CAPACITY_FULL);
        }
        return Result.success();
    }
}
