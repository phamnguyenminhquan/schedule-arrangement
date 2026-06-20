package services.RegistrationServices;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import models.Section;
import models.Student;
import models.Subject;
import repositories.SectionRepo;
import repositories.StudentRepo;
import repositories.SubjectRepo;
import services.RegistrationCheckers.DuplicateSectionChecker;
import services.RegistrationCheckers.DuplicateSubjectChecker;
import services.RegistrationCheckers.RegistrationChecker;
import services.RegistrationCheckers.ScheduleConflictChecker;
import services.RegistrationCheckers.SectionCapacityChecker;
import services.StorageServices.StorageService;
import services.StorageServices.StudentStorage;
import services.responses.CheckResult;

public class RegistrationService {
    private final StudentRepo studentRepo;
    private final SubjectRepo subjectRepo;
    private final SectionRepo sectionRepo;
    private final List<RegistrationChecker> checkers;

    private final StorageService studentStorage;

    public RegistrationService() {
        this.studentRepo = StudentRepo.getInstance();
        this.subjectRepo = SubjectRepo.getInstance();
        this.sectionRepo = SectionRepo.getInstance();

        this.checkers = new ArrayList<>();
        checkers.add(new SectionCapacityChecker());
        checkers.add(new DuplicateSectionChecker());
        checkers.add(new DuplicateSubjectChecker(sectionRepo));
        checkers.add(new ScheduleConflictChecker(sectionRepo));

        this.studentStorage = new StudentStorage(Path.of("data/student.txt"));
    }

    public void register(String studentId, String sectionId) {
        // step 1: load data from storage
        studentStorage.loadData();

        // step 2: get target objects from repo
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

        // step 3: registration checkers
        for (RegistrationChecker checker : checkers) {
            CheckResult result = checker.check(student, subject, section);

            if (!result.isValid()) {
                System.out.println("Registration failed: " + result.getMessage());
                // TODO: handle the alternative logics later
                return;
            }
        }

        // step 4: update data
        student.add(sectionId);
        section.add(studentId);

        System.out.println("Registration succeeded: " + student.getName() + " registered " + subject.getName()
                + ", class " + section.getSectionCode());
    }
}
