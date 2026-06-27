package services.RegistrationCheckers;

import models.Section;
import models.Student;
import models.Subject;
import services.responses.Result;
import services.responses.ErrorType;

public class DuplicateSectionChecker implements RegistrationChecker {
    @Override
    public Result check(Student student, Subject subject, Section section) {
        if (student.hasRegistered(section.getId())) {
            String message = "Section[id=" + section.getId() + "] is already registered";
            return Result.fail(message, ErrorType.DUPLICATE_SECTIONS);
        }
        return Result.success();
    }
}