package services.RegistrationCheckers;

import java.util.List;

import models.Section;
import models.Student;
import models.Subject;
import repositories.SectionRepo;
import services.responses.Result;
import services.responses.ErrorType;

public class ScheduleConflictChecker implements RegistrationChecker {
    private final SectionRepo sectionRepo;

    public ScheduleConflictChecker(SectionRepo sectionRepo) {
        this.sectionRepo = sectionRepo;
    }

    @Override
    public Result check(Student student, Subject subject, Section section) {
        List<Section> registeredSections = this.sectionRepo.findAllById(student.getSectionIds());

        List<Section> conflictedSections = registeredSections.stream()
                .filter((oldSection) -> oldSection.conflictsWith(section))
                .toList();

        if (!conflictedSections.isEmpty()) {
            StringBuilder messageBuilder = new StringBuilder();

            messageBuilder.append("Section[id=").append(section.getId()).append("] conflicts with Sections: ");
            for (Section s : conflictedSections) {
                messageBuilder.append(s.getId()).append(" ");
            }
            return Result.fail(messageBuilder.toString().trim(), ErrorType.SCHEDULE_CONFLICT);
        }
        return Result.success();
    }
}
