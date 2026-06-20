package services.StorageServices;

import models.Student;
import repositories.StudentRepo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class StudentStorage implements StorageService {
    private final Path filePath;
    private final StudentRepo studentRepo;

    public StudentStorage(Path filePath) {
        this.filePath = filePath;
        this.studentRepo = StudentRepo.getInstance();
    }

    @Override
    public void loadData() {

        // step 1: check if file existed
        if (!Files.exists(filePath)) {
            System.err.println("File not found: " + filePath.toAbsolutePath());
            return;
        }

        // step 2: read file
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {

                // step 2.1: transfer data string to data array
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] data = line.split(",");

                // step 2.2: assign data to student and add to repo
                if (data.length >= 2) {
                    String studentId = data[0].trim();
                    String studentName = data[1].trim();

                    Student student = new Student(studentId, studentName);

                    // load sectionIds if existed
                    for (int i = 2; i < data.length; i++) {
                        student.add(data[i].trim());
                    }

                    // add to repo
                    studentRepo.add(student);
                }
            }
        } catch (IOException e) {
            System.err.println("Error when reading file: " + e.getMessage());
        }
    }

    @Override
    public void saveData() {

        // step 1: make sure if data folder is existed
        try {
            if (filePath.getParent() != null) {
                Files.createDirectories(filePath.getParent());
            }
        } catch (IOException e) {
            System.err.println("Error when creating storage folder: " + e.getMessage());
            return;
        }

        // step 2: write data
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (Student student : studentRepo.getStudentList()) {

                // step 2.1: create a data string
                StringBuilder sb = new StringBuilder();
                sb.append(student.getId()).append(",").append(student.getName());

                for (String sectionId : student.getSectionIds()) {
                    sb.append(",").append(sectionId);
                }

                // step 2.2: write file
                writer.write(sb.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error when writing file: " + e.getMessage());
        }
    }
}
