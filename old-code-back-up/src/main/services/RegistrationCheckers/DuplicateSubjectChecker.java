package main.services.RegistrationCheckers;

import java.util.List;

import main.models.Section;
import main.models.Student;
import main.models.Subject;
import main.repositories.SectionRepo;
import main.services.responses.CheckResult;
import main.services.responses.ErrorType;

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
