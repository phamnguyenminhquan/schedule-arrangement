import java.util.List;
import java.util.Scanner;

import config.AppConfig;
import models.Section;
import models.Student;
import models.Subject;
import repositories.SectionRepo;
import repositories.StudentRepo;
import repositories.SubjectRepo;
import services.RegistrationServices.RegistrationService;
import services.responses.CheckResult;

public class App {
    private static Scanner scanner = new Scanner(System.in);

    static void main() {
        try {
            // initialize AppConfig
            AppConfig.initialize();

            boolean isRunning = true;
            do {
                printMenu();
                System.out.print("Your choice: ");
                int choice = scanner.nextInt();

                // loading repo
                AppConfig.repoLoading();

                switch (choice) {
                    case 0 -> {
                        isRunning = false;
                    }
                    case 1 -> {
                        printAllSections(AppConfig.getSectionRepo());
                    }
                    case 2 -> {
                        printAllStudents(AppConfig.getStudentRepo());
                    }
                    case 3 -> {
                        printAllSubjects(AppConfig.getSubjectRepo());
                    }
                    case 4 -> {
                        RegistrationService registrationService = AppConfig.getRegistrationservice();

                        System.out.print("\nEnter student ID: ");
                        String studentId = scanner.next();
                        System.out.print("Enter section ID: ");
                        String sectionId = scanner.next();

                        CheckResult registrationResult = registrationService.register(studentId, sectionId);
                        if (registrationResult.isValid()) {
                            System.out.println("Registration succeeded");
                        } else {
                            System.err.println(registrationResult.getMessage());
                        }
                    }
                    case 5 -> {
                        RegistrationService registrationService = AppConfig.getRegistrationservice();

                        System.out.print("\nEnter student ID: ");
                        String studentId = scanner.next();
                        System.out.print("Enter section ID: ");
                        String sectionId = scanner.next();

                        CheckResult unregistrationResult = registrationService.unregister(studentId, sectionId);
                        if (unregistrationResult.isValid()) {
                            System.out.println("Unregistration succeeded");
                        } else {
                            System.err.println(unregistrationResult.getMessage());
                        }
                    }
                    default -> {
                        System.out.println("Option " + choice + " is unavailable");
                    }
                }

                // saving repo
                AppConfig.repoSaving();

            } while (isRunning);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void printMenu() {
        String menu = """
                --- Schedule Arragement ---
                0. Exit
                1. Print all sections
                2. Print all students
                3. Print all subjects
                4. Register
                5. Unregister
                """;
        System.out.print(menu);
    }

    private static void printAllSections(SectionRepo sectionRepo) {
        List<Section> sections = sectionRepo.getSectionList();

        StringBuilder sb = new StringBuilder();
        sb.append("-------------------\n");

        for (Section section : sections) {
            sb.append(section).append("\n");
        }
        sb.append("-------------------\n");

        System.out.println(sb.toString());
    }

    private static void printAllStudents(StudentRepo studentRepo) {
        List<Student> students = studentRepo.getStudentList();

        StringBuilder sb = new StringBuilder();
        sb.append("-------------------\n");

        for (Student student : students) {
            sb.append(student).append("\n");
        }
        sb.append("-------------------\n");

        System.out.println(sb.toString());
    }

    private static void printAllSubjects(SubjectRepo subjectRepo) {
        List<Subject> subjects = subjectRepo.getSubjectList();

        StringBuilder sb = new StringBuilder();
        sb.append("-------------------\n");

        for (Subject subject : subjects) {
            sb.append(subject).append("\n");
        }
        sb.append("-------------------\n");

        System.out.println(sb.toString());
    }
}
