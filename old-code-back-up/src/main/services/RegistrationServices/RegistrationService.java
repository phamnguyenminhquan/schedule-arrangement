package main.services.RegistrationServices;

import java.util.ArrayList;
import java.util.List;

import main.models.Section;
import main.models.Student;
import main.models.Subject;
import main.repositories.SectionRepo;
import main.repositories.StudentRepo;
import main.repositories.SubjectRepo;
import main.services.RegistrationCheckers.DuplicateSectionChecker;
import main.services.RegistrationCheckers.DuplicateSubjectChecker;
import main.services.RegistrationCheckers.RegistrationChecker;
import main.services.RegistrationCheckers.ScheduleConflictChecker;
import main.services.RegistrationCheckers.SectionCapacityChecker;
import main.services.responses.CheckResult;

public class RegistrationService {
    private final StudentRepo studentRepo;
    private final SubjectRepo subjectRepo;
    private final SectionRepo sectionRepo;
    private final List<RegistrationChecker> checkers;

    public RegistrationService() {
        this.studentRepo = StudentRepo.getInstance();
        this.subjectRepo = SubjectRepo.getInstance();
        this.sectionRepo = SectionRepo.getInstance();

        this.checkers = new ArrayList<>();
        checkers.add(new SectionCapacityChecker());
        checkers.add(new DuplicateSectionChecker());
        checkers.add(new DuplicateSubjectChecker(sectionRepo));
        checkers.add(new ScheduleConflictChecker(sectionRepo));
    }

    public void register(String studentId, String sectionId) {
        // step 1: loading datas
        Student student = studentRepo.findById(studentId);
        if (student == null) {
            throw new IllegalArgumentException("Student not found with id=" + studentId);
        }

        Section section = sectionRepo.findById(sectionId);
        if (section == null) {
            throw new IllegalArgumentException("Section not found with id=" + sectionId);
        }

        Subject subject = subjectRepo.findById(section.getSubjectId());
        if (subject == null) {
            throw new IllegalArgumentException("Subject not found with id=" + section.getSubjectId());
        }

        // step 2: calling registration checkers
        for (RegistrationChecker checker : checkers) {
            CheckResult result = checker.check(student, subject, section);

            if (!result.isValid()) {
                System.out.println("Registration failed: " + result.getMessage());
                // TODO: handle the alternative logics later
                return;
            }
        }

        // step 3: updating datas
        student.update(sectionId);
        section.update(studentId);

        System.out.println("Registration succeeded: " + student.getName() + " registered " + subject.getName()
                + ", class " + section.getSectionCode());
    }
}
