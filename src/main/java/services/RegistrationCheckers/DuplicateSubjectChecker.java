package services.RegistrationCheckers;

import java.util.List;

import models.Section;
import models.Student;
import models.Subject;
import repositories.SectionRepo;
import services.responses.CheckResult;
import services.responses.ErrorType;

public class DuplicateSubjectChecker implements RegistrationChecker {
    private final SectionRepo sectionRepo;

    public DuplicateSubjectChecker(SectionRepo sectionRepo) {
        this.sectionRepo = sectionRepo;
    }

    @Override
    public CheckResult check(Student student, Subject subject, Section section) {
        List<Section> registeredSections = this.sectionRepo.findAllById(student.getSectionIds());
        boolean isRegistered = registeredSections.stream()
                .anyMatch((oldSection) -> oldSection.getSubjectId().equals(subject.getId()));

        if (isRegistered) {
            String message = "Subject[id=" + subject.getId() + "] is already registered";
            return CheckResult.fail(message, ErrorType.DUPLICATE_SUBJECTS);
        }
        return CheckResult.success();
    }
}
