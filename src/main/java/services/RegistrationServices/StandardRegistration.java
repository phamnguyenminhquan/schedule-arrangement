package services.RegistrationServices;

import java.util.Map;

import models.Section;
import models.Student;
import models.Subject;
import repositories.SectionRepo;
import repositories.StudentRepo;
import repositories.SubjectRepo;
import services.RegistrationCheckers.RegistrationChecker;
import services.responses.CheckResult;
import services.responses.ErrorType;

public class StandardRegistration implements RegistrationService {
    private final StudentRepo studentRepo;
    private final SubjectRepo subjectRepo;
    private final SectionRepo sectionRepo;
    private final Map<ErrorType, RegistrationChecker> checkerMap;

    private Student targetStudent;
    private Section targetSection;
    private Subject targetSubject;

    public StandardRegistration(StudentRepo studentRepo, SubjectRepo subjectRepo, SectionRepo sectionRepo,
            Map<ErrorType, RegistrationChecker> checkerMap) {
        this.studentRepo = studentRepo;
        this.subjectRepo = subjectRepo;
        this.sectionRepo = sectionRepo;
        this.checkerMap = checkerMap;
    }

    @Override
    public CheckResult register(String studentId, String sectionId) {
        // step 1: get target objects from repo
        CheckResult targetInitializedResult = findAndSetTargets(studentId, sectionId);
        if (!targetInitializedResult.isValid()) {
            return CheckResult.fail(targetInitializedResult.getMessage(), targetInitializedResult.getErrorType());
        }

        // step 2: registration checkers
        for (RegistrationChecker checker : checkerMap.values()) {
            CheckResult checkResult = checker.check(targetStudent, targetSubject, targetSection);

            if (!checkResult.isValid()) {
                String message = "Registration failed: " + checkResult.getMessage();
                return CheckResult.fail(message, checkResult.getErrorType());
            }
        }

        // step 3: update data
        targetStudent.add(sectionId);
        targetSection.add(studentId);

        reset();
        return CheckResult.success();
    }

    @Override
    public CheckResult unregister(String studentId, String sectionId) {
        // step 1: get target objects from repo
        CheckResult targetInitializedResult = findAndSetTargets(studentId, sectionId);
        if (!targetInitializedResult.isValid()) {
            return CheckResult.fail(targetInitializedResult.getMessage(), targetInitializedResult.getErrorType());
        }

        // step 2: check if student has registered section
        CheckResult checkResult = checkerMap.get(ErrorType.DUPLICATE_SECTIONS)
                .check(targetStudent, targetSubject, targetSection);

        if (checkResult.isValid()) {
            String message = "Unregistration failed: Student[id=" + targetStudent.getId()
                    + "] has not registered Section[id=" + targetSection.getId() + "] yet";
            return CheckResult.fail(message, checkResult.getErrorType());
        }

        // step 3: update data
        targetStudent.remove(targetSection.getId());
        targetSection.remove(targetStudent.getId());

        reset();
        return CheckResult.success();
    }

    // ------------------------
    // private helper functions
    // ------------------------

    private CheckResult findAndSetTargets(String studentId, String sectionId) {
        targetStudent = studentRepo.findById(studentId);
        if (targetStudent == null) {
            reset();
            return CheckResult.fail("Student not found with id=" + studentId, ErrorType.ID_NOT_FOUND);
        }

        targetSection = sectionRepo.findById(sectionId);
        if (targetSection == null) {
            reset();
            return CheckResult.fail("Section not found with id=" + sectionId, ErrorType.ID_NOT_FOUND);
        }

        targetSubject = subjectRepo.findById(targetSection.getSubjectId());
        if (targetSubject == null) {
            reset();
            return CheckResult.fail("Subject not found with id=" + targetSection.getSubjectId(),
                    ErrorType.ID_NOT_FOUND);
        }

        return CheckResult.success();
    }

    // reset targets for next proccess
    private void reset() {
        targetStudent = null;
        targetSection = null;
        targetSubject = null;
    }
}