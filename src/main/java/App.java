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
import services.responses.Result;

public class App {
    private static Scanner scanner = new Scanner(System.in);
    private static String horizontalLine = "--------------------------------------------";

    static void main() {
        // initialize AppConfig
        try {
            AppConfig.initialize();
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.err.println("Error when initializing AppConfig: " + e.getMessage());
        }

        // main
        try {
            boolean isRunning = true;
            do {
                int choice = printMenuAndGetChoice();

                // load repo
                repoLoadingProccess(AppConfig.repoLoading());

                switch (choice) {
                    case 0 -> {
                        // exit
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
                        // registration

                        System.out.print("Enter student ID: ");
                        String studentId = scanner.nextLine();
                        System.out.print("Enter section ID: ");
                        String sectionId = scanner.nextLine();

                        registerProccess(AppConfig.getRegistrationservice(), studentId, sectionId);
                    }
                    case 5 -> {
                        // unregistration

                        System.out.print("Enter student ID: ");
                        String studentId = scanner.nextLine();
                        System.out.print("Enter section ID: ");
                        String sectionId = scanner.nextLine();

                        unregisterProccess(AppConfig.getRegistrationservice(), studentId, sectionId);
                    }
                    case 6 -> {
                        // add student

                        System.out.print("Enter student ID: ");
                        String studentId = scanner.nextLine();
                        System.out.print("Enter student name: ");
                        String studentName = scanner.nextLine();

                        Student student = Student.builder()
                                .id(studentId)
                                .name(studentName)
                                .build();

                        addStudentProccess(AppConfig.getStudentRepo(), student);
                    }
                    case 7 -> {
                        // add subject

                        System.out.print("Enter subject ID: ");
                        String subjectId = scanner.nextLine();
                        System.out.print("Enter subject name: ");
                        String subjectName = scanner.nextLine();
                        System.out.print("Enter number of credits: ");
                        int noCredits = inputInt();

                        Subject subject = Subject.builder()
                                .id(subjectId)
                                .name(subjectName)
                                .noCredits(noCredits)
                                .build();

                        addSubjectProccess(AppConfig.getSubjectRepo(), subject);
                    }
                    default -> {
                        System.out.println("Option " + choice + " is unavailable");
                    }
                }

                // save repo
                repoSavingProcess(AppConfig.repoSaving());

            } while (isRunning);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    // #region Helper functions

    private static int inputInt() {
        int input = scanner.nextInt();
        scanner.nextLine(); // consume "\n"
        return input;
    }

    private static int printMenuAndGetChoice() {
        String menu = """
                --- Schedule Arragement ---
                0. Exit
                1. Print all sections
                2. Print all students
                3. Print all subjects
                4. Register
                5. Unregister
                6. Add student
                7. Add subject
                """;
        System.out.println(menu);
        System.out.println(horizontalLine);
        System.out.print("Your choice: ");
        int choice = inputInt();
        System.out.println(horizontalLine);

        return choice;
    }

    private static void repoLoadingProccess(Result loadResult) {
        System.out.println(">>> Loading repo ...");

        if (!loadResult.isValid()) {
            System.out.println(">>> Loading error: " + loadResult.getMessage());
            System.out.println(">>> Error type: " + loadResult.getErrorType());
        } else {
            System.out.println(">>> Loading successfully");
        }

        System.out.println(horizontalLine);
    }

    private static void repoSavingProcess(Result saveResult) {
        System.out.println(">>> Saving repo ...");

        if (!saveResult.isValid()) {
            System.out.println(">>> Saving error: " + saveResult.getMessage());
            System.out.println(">>> Error type: " + saveResult.getErrorType());
        } else {
            System.out.println(">>> Saving successfully");
        }

        System.out.println(horizontalLine);
    }

    private static void printAllSections(SectionRepo sectionRepo) {
        List<Section> sections = sectionRepo.getSectionList();

        StringBuilder sb = new StringBuilder();
        sb.append("--- All Sections ---\n");

        for (Section section : sections) {
            sb.append(section).append("\n");
        }

        System.out.println(sb);
        System.out.println(horizontalLine);
    }

    private static void printAllStudents(StudentRepo studentRepo) {
        List<Student> students = studentRepo.getStudentList();

        StringBuilder sb = new StringBuilder();
        sb.append("--- All Students ---\n");

        for (Student student : students) {
            sb.append(student).append("\n");
        }

        System.out.println(sb);
        System.out.println(horizontalLine);
    }

    private static void printAllSubjects(SubjectRepo subjectRepo) {
        List<Subject> subjects = subjectRepo.getSubjectList();

        StringBuilder sb = new StringBuilder();
        sb.append("--- All Subjects ---\n");

        for (Subject subject : subjects) {
            sb.append(subject).append("\n");
        }

        System.out.println(sb);
        System.out.println(horizontalLine);
    }

    private static void registerProccess(RegistrationService registrationService, String studentId, String sectionId) {
        Result registrationResult = registrationService.register(studentId, sectionId);

        System.out.println("--- Registration ---");

        if (registrationResult.isValid()) {
            System.out.println(">>> Registration successfully");
        } else {
            System.out.println(">>> Registration failed: " + registrationResult.getMessage());
            System.out.println(">>> Error type: " + registrationResult.getErrorType());
        }

        System.out.println(horizontalLine);
    }

    private static void unregisterProccess(RegistrationService registrationService, String studentId,
            String sectionId) {
        Result unregistrationResult = registrationService.unregister(studentId, sectionId);

        System.out.println("--- Unregistration ---");

        if (unregistrationResult.isValid()) {
            System.out.println(">>> Unregistration successfully");
        } else {
            System.out.println(">>> Unregistration failed: " + unregistrationResult.getMessage());
            System.out.println(">>> Error type: " + unregistrationResult.getErrorType());
        }

        System.out.println(horizontalLine);
    }

    private static void addStudentProccess(StudentRepo studentRepo, Student student) {
        System.out.println("--- Add Student ---");

        if (studentRepo.findById(student.getId()) == null) {
            studentRepo.add(student);
            System.out.println(">>> Adding new student successfully");
        } else {
            System.out.println(">>> Student[id=" + student.getId() + "] is already existed");
        }

        System.out.println(horizontalLine);
    }

    private static void addSubjectProccess(SubjectRepo subjectRepo, Subject subject) {
        System.out.println("--- Add subject ---");

        if (subjectRepo.findById(subject.getId()) == null) {
            subjectRepo.add(subject);
            System.out.println(">>> Adding new subject successfully");
        } else {
            System.out.println(">>> Subject[id=" + subject.getId() + "] is already existed");
        }

        System.out.println(horizontalLine);
    }

    // #endregion
}
