package services.RegistrationServices;

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
import services.responses.CheckResult;

public class StandardRegistration implements RegistrationService {
    private final StudentRepo studentRepo;
    private final SubjectRepo subjectRepo;
    private final SectionRepo sectionRepo;
    private final List<RegistrationChecker> checkers;

    public StandardRegistration(StudentRepo studentRepo, SubjectRepo subjectRepo, SectionRepo sectionRepo) {
        this.studentRepo = studentRepo;
        this.subjectRepo = subjectRepo;
        this.sectionRepo = sectionRepo;

        checkers = new ArrayList<>();
        checkers.add(new SectionCapacityChecker());
        checkers.add(new DuplicateSectionChecker());
        checkers.add(new DuplicateSubjectChecker(sectionRepo));
        checkers.add(new ScheduleConflictChecker(sectionRepo));
    }

    @Override
    public void register(String studentId, String sectionId) {
        // step 1: load data
        studentRepo.load();
        subjectRepo.load();
        sectionRepo.load();

        // step 2: get target objects from repo
        Student targetStudent = getTargetStudentOrThrow(studentId);
        Section targetSection = getTargetSectionOrThrow(sectionId);
        Subject targetSubject = getTargetSubjectOrThrow(targetSection.getSubjectId());

        // step 3: registration checkers
        for (RegistrationChecker checker : checkers) {
            CheckResult checkResult = checker.check(targetStudent, targetSubject, targetSection);

            if (!checkResult.isValid()) {
                System.err.println("Registration failed: " + checkResult.getMessage());
                return;
            }
        }

        // step 4: update and save
        targetStudent.add(sectionId);
        targetSection.add(studentId);

        studentRepo.save();
        subjectRepo.save();
        sectionRepo.save();

        System.out.println("Registration succeeded: " + targetStudent.getName() + " registered "
                + targetSubject.getName() + ", class " + targetSection.getSectionCode());
    }

    // ------------------------
    // private helper functions
    // ------------------------

    private Student getTargetStudentOrThrow(String studentId) {
        Student targetStudent = studentRepo.findById(studentId);
        if (targetStudent == null) {
            throw new IllegalArgumentException("Student not found with id=" + studentId);
        }
        return targetStudent;
    }

    private Section getTargetSectionOrThrow(String sectionId) {
        Section targetSection = sectionRepo.findById(sectionId);
        if (targetSection == null) {
            throw new IllegalArgumentException("Section not found with id=" + sectionId);
        }
        return targetSection;
    }

    private Subject getTargetSubjectOrThrow(String subjectId) {
        Subject targetSubject = subjectRepo.findById(subjectId);
        if (targetSubject == null) {
            throw new IllegalArgumentException("Subject not found with id=" + subjectId);
        }
        return targetSubject;
    }
}