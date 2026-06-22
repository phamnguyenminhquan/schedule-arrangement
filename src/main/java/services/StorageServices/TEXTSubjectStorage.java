package services.StorageServices;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import models.Subject;

public class TEXTSubjectStorage implements StorageService<Subject> {
    private final Path filePath;

    public TEXTSubjectStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Subject> loadData() {
        List<Subject> subjects = new ArrayList<>();

        // step 1: check if file is existed
        if (!Files.exists(filePath)) {
            System.err.println("Find not found, return empty list: " + filePath.toAbsolutePath());
            return subjects;
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

                // step 5: assign data to subject and add to list
                String subjectId = data[0].trim();
                String subjectName = data[1].trim();
                int noCredits = Integer.parseInt(data[2].trim());

                Subject subject = new Subject(subjectId, subjectName, noCredits);
                subjects.add(subject);
            }
        } catch (IOException e) {
            System.err.println("Error when writing file: " + e.getMessage());
        }
        return subjects;
    }

    @Override
    public void saveData(List<Subject> data) {
        // step 1: make sure the storage folder is existed
        try {
            if (filePath.getParent() != null) {
                Files.createDirectories(filePath.getParent());
            }
        } catch (IOException e) {
            System.err.println("Error when creating storage folder: " + e.getMessage());
        }

        // step 2: initilize bufferedWriter with try-with-resources
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (Subject subject : data) {
                // step 3: create data string
                String str = subject.getId() + "," + subject.getName() + "," + subject.getNoCredits();

                // step 4: write to file
                writer.write(str);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error when writing file: " + e.getMessage());
        }
    }
}
