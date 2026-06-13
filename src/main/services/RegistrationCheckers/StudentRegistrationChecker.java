package main.services.RegistrationCheckers;

import main.exceptions.StudentHasRegisteredException;
import main.models.Section;
import main.models.Student;
import main.models.Subject;

public class StudentRegistrationChecker implements RegistrationChecker {
    @Override
    public void check(Student student, Subject subject, Section section) {
        if (student.hasRegistered(section.getId())) {
            throw new StudentHasRegisteredException("Section[id=" + section.getId() + "] is already registered");
        }
    }
}