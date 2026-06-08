package main.services.implementation;

import main.exceptions.NullSectionException;
import main.exceptions.SectionConflictException;
import main.exceptions.SectionFullException;
import main.models.Section;
import main.models.Student;
import main.models.Subject;
import main.models.SubjectRecord;
import main.services.IRegistrationService;

public class StandardRegistration implements IRegistrationService {
    @Override
    public void registerSubject(Student student, Subject subject, String sectionId) {
        // Step 1: Get the target section from the subject
        Section target = subject.getSections()
                .stream()
                .filter((section) -> section.getId().equals(sectionId))
                .findAny()
                .orElse(null);

        // Step 2: Check if target section is available
        if (target == null) {
            throw new NullSectionException("Section[id=" + sectionId + "] not found");
        }
        if (target.isFull()) {
            throw new SectionFullException("Section[id=" + target.getId() + "] is full");
        }

        // Step 3: Check if target section conflicts with other enrolled sections
        boolean isConflicted = student.getRecords()
                .stream()
                .anyMatch((record) -> record.getEnrolledSection().conflictsWith(target));

        if (isConflicted) {
            throw new SectionConflictException("Section[id=" + target.getId() + "] is conflicted");
        }

        // Step 4: Create a subject record to register
        target.update();
        SubjectRecord record = new SubjectRecord(subject.getId(), subject.getName(), target);
        student.getRecords().add(record);
    }
}
