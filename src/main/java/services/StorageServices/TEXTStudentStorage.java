package services.StorageServices;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import models.Student;

public class TEXTStudentStorage implements StorageService<Student> {
    private final Path filePath;

    public TEXTStudentStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Student> loadData() {
        List<Student> students = new ArrayList<>();

        // step 1: check if file is existed
        if (!Files.exists(filePath)) {
            System.err.println("File not found, return empty list: " + filePath.toAbsolutePath());
            return students;
        }

        // step 2: initialize bufferedReader with try-with-resources
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            // step 3: read separate line
            String line;
            while ((line = reader.readLine()) != null) {
                // step 4: transfer data string to array
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] data = line.split(",");

                // step 5: assign data to student and add to list
                if (data.length >= 2) {
                    String studentId = data[0].trim();
                    String studentName = data[1].trim();

                    Student student = new Student(studentId, studentName);

                    // add sectionIds if existed
                    for (int i = 2; i < data.length; i++) {
                        String sectionId = data[i].trim();
                        student.add(sectionId);
                    }

                    students.add(student);
                }
            }
        } catch (IOException e) {
            System.err.println("Error when reading file: " + e.getMessage());
        }
        return students;
    }

    @Override
    public void saveData(List<Student> data) {
        // step 1: make sure the storage folder is existed
        try {
            if (filePath.getParent() != null) {
                Files.createDirectories(filePath.getParent());
            }
        } catch (IOException e) {
            System.err.println("Error when creating storage folder: " + e.getMessage());
        }

        // step 2: initialize bufferedWriter with try-with-resources
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (Student student : data) {
                // step 3: create data string
                StringBuilder sb = new StringBuilder();
                sb.append(student.getId()).append(",").append(student.getName());

                for (String sectionId : student.getSectionIds()) {
                    sb.append(",").append(sectionId);
                }

                // step 4: write to file
                writer.write(sb.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error when writing file: " + e.getMessage());
        }
    }
}
