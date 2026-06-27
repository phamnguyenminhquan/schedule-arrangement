package services.RegistrationServices;

import java.util.Map;

import models.Section;
import models.Student;
import models.Subject;
import repositories.SectionRepo;
import repositories.StudentRepo;
import repositories.SubjectRepo;
import services.RegistrationCheckers.RegistrationChecker;
import services.responses.Result;
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
    public Result register(String studentId, String sectionId) {
        // step 1: get target objects from repo
        Result targetInitialized = findAndSetTargets(studentId, sectionId);
        if (!targetInitialized.isValid()) {
            return targetInitialized;
        }

        // step 2: registration checkers
        for (RegistrationChecker checker : checkerMap.values()) {
            Result checkResult = checker.check(targetStudent, targetSubject, targetSection);

            if (!checkResult.isValid()) {
                return checkResult;
            }
        }

        // step 3: update data
        targetStudent.add(targetSection.getId());
        targetSection.add(targetStudent.getId());

        return Result.success();
    }

    @Override
    public Result unregister(String studentId, String sectionId) {
        // step 1: get target objects from repo
        Result targetInitializedResult = findAndSetTargets(studentId, sectionId);
        if (!targetInitializedResult.isValid()) {
            return targetInitializedResult;
        }

        // step 2: check if student has registered section
        Result checkResult = checkerMap.get(ErrorType.DUPLICATE_SECTIONS)
                .check(targetStudent, targetSubject, targetSection);

        if (checkResult.isValid()) {
            String message = "Student[id=" + targetStudent.getId()
                    + "] has not registered Section[id=" + targetSection.getId() + "] yet";
            return Result.fail(message, ErrorType.UNAVAILABLE_SECTION);
        }

        // step 3: update data
        targetStudent.remove(targetSection.getId());
        targetSection.remove(targetStudent.getId());

        return Result.success();
    }

    // ------------------------
    // private helper functions
    // ------------------------

    private Result findAndSetTargets(String studentId, String sectionId) {
        // reset targets to null
        reset();

        targetStudent = studentRepo.findById(studentId);
        if (targetStudent == null) {
            return Result.fail("Student not found with id=" + studentId, ErrorType.ID_NOT_FOUND);
        }

        targetSection = sectionRepo.findById(sectionId);
        if (targetSection == null) {
            return Result.fail("Section not found with id=" + sectionId, ErrorType.ID_NOT_FOUND);
        }

        String subjectId = targetSection.getId();
        targetSubject = subjectRepo.findById(subjectId);
        if (targetSubject == null) {
            return Result.fail("Subject not found with id=" + subjectId,
                    ErrorType.ID_NOT_FOUND);
        }

        return Result.success();
    }

    // reset targets for next proccess
    private void reset() {
        targetStudent = null;
        targetSection = null;
        targetSubject = null;
    }
}