import java.time.DayOfWeek;
import java.time.LocalTime;

import models.Section;
import models.Slot;
import models.Student;
import models.Subject;
import repositories.SectionRepo;
import repositories.StudentRepo;
import repositories.SubjectRepo;
import services.RegistrationServices.RegistrationService;

public class App {
    static void main() {
        Slot slot1 = new Slot(DayOfWeek.MONDAY, LocalTime.of(7, 30), LocalTime.of(9, 30));
        Slot slot2 = new Slot(DayOfWeek.TUESDAY, LocalTime.of(7, 30), LocalTime.of(9, 30));
        Slot slot3 = new Slot(DayOfWeek.WEDNESDAY, LocalTime.of(7, 30), LocalTime.of(9, 30));
        Slot slot4 = new Slot(DayOfWeek.THURSDAY, LocalTime.of(7, 30), LocalTime.of(9, 30));
        Slot slot5 = new Slot(DayOfWeek.FRIDAY, LocalTime.of(7, 30), LocalTime.of(9, 30));
        Slot slot6 = new Slot(DayOfWeek.SATURDAY, LocalTime.of(7, 30), LocalTime.of(9, 30));

        Subject subject1 = new Subject("cse100", "Introduction to CIT", 4);
        Subject subject2 = new Subject("cse103", "Problem solving 1", 4);
        Subject subject3 = new Subject("cse104", "Problem solving 2", 4);
        Subject subject4 = new Subject("cse201", "DSA", 4);
        Subject subject5 = new Subject("cse203", "OOP", 4);

        Student student1 = new Student("id-001", "Nguyen Van A");
        Student student2 = new Student("id-002", "Nguyen Van B");
        Student student3 = new Student("id-003", "Nguyen Van C");

        Section section1 = new Section("sec-001", "01", subject1.getId(), 2);
        section1.getSlots().add(slot1);
        section1.getSlots().add(slot2);
        section1.getSlots().add(slot3);

        Section section2 = new Section("sec-002", "02", subject1.getId(), 0);
        section2.getSlots().add(slot4);
        section2.getSlots().add(slot5);
        section2.getSlots().add(slot6);

        Section section3 = new Section("sec-003", "03", subject1.getId(), 1);
        section3.getSlots().add(slot4);
        section3.getSlots().add(slot5);
        section3.getSlots().add(slot6);

        Section section4 = new Section("sec-004", "02", subject2.getId(), 1);
        section4.getSlots().add(slot1);
        section4.getSlots().add(slot3);
        section4.getSlots().add(slot5);

        // Repositories
        SubjectRepo subjectRepo = SubjectRepo.getInstance();
        subjectRepo.add(subject1);
        subjectRepo.add(subject2);
        subjectRepo.add(subject3);
        subjectRepo.add(subject4);
        subjectRepo.add(subject5);

        StudentRepo studentRepo = StudentRepo.getInstance();
        studentRepo.add(student1);
        studentRepo.add(student2);
        studentRepo.add(student3);

        SectionRepo sectionRepo = SectionRepo.getInstance();
        sectionRepo.add(section1);
        sectionRepo.add(section2);
        sectionRepo.add(section3);
        sectionRepo.add(section4);

        // Registration service
        RegistrationService registrationService = new RegistrationService();

        try {
            System.out.println("Case 1: successful");
            registrationService.register("id-001", "sec-001");

            System.out.println("Case 2: schedule conflict");
            registrationService.register("id-001", "sec-004");

            System.out.println("Case 3: duplicate sections");
            registrationService.register("id-001", "sec-001");

            System.out.println("Case 4: capacity full");
            registrationService.register("id-001", "sec-002");

            System.out.println("Case 5: duplicate subjects");
            registrationService.register("id-001", "sec-003");

            System.out.println("Case 6: ids not found");
            registrationService.register("id-111", "sec-002");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
