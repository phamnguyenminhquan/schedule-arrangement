package main.services.RegistrationServices;

import java.util.List;

import main.exceptions.SectionConflictException;
import main.models.Section;
import main.models.Student;
import main.models.Subject;
import main.repositories.SectionRepo;
import main.repositories.StudentRepo;
import main.repositories.SubjectRepo;
import main.services.RegistrationCheckers.RegistrationChecker;

public class RegistrationService {
    private final StudentRepo studentRepo;
    private final SubjectRepo subjectRepo;
    private final SectionRepo sectionRepo;
    private final List<RegistrationChecker> checkers;

    public RegistrationService(StudentRepo studentRepo, SubjectRepo subjectRepo, SectionRepo sectionRepo,
            List<RegistrationChecker> checkers) {
        this.studentRepo = studentRepo;
        this.subjectRepo = subjectRepo;
        this.sectionRepo = sectionRepo;
        this.checkers = checkers;
    }

    public StudentRepo getStudentRepo() {
        return studentRepo;
    }

    public SubjectRepo getSubjectRepo() {
        return subjectRepo;
    }

    public SectionRepo getSectionRepo() {
        return sectionRepo;
    }

    public List<RegistrationChecker> getCheckers() {
        return checkers;
    }

    public void register(String studentId, String sectionId) {
        // step 1: loading datas from the Repos
        Student student = this.studentRepo.findById(studentId);
        Section targetSection = this.sectionRepo.findById(sectionId);

        if (student == null || targetSection == null) {
            throw new IllegalArgumentException("loading failed, ids not found");
        }

        Subject subject = this.subjectRepo.findById(targetSection.getSubjectId());
        if (subject == null) {
            throw new IllegalArgumentException("loading failed, ids not found");
        }

        // step 2: checking
        for (RegistrationChecker checker : this.checkers) {
            checker.check(student, subject, targetSection);
        }

        // step 3: checking conflicts
        List<Section> registeredSections = this.sectionRepo.findAllById(student.getSectionIds());

        for (Section oldSection : registeredSections) {
            if (oldSection.conflictsWith(targetSection)) {
                throw new SectionConflictException("Section[id=" + targetSection.getId()
                        + "] conflicts with Section[id=" + oldSection.getId() + "]");
            }
        }

        // step 4: registering
        student.update(targetSection.getId());
        targetSection.update(student.getId());
    }
}
