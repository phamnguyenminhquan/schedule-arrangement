package main.services;

import main.models.Student;
import main.models.Subject;

public interface IRegistrationService {
    void registerSubject(Student student, Subject subject, String sectionId);
}
