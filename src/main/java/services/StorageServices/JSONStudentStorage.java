package services.StorageServices;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import models.Student;

public class JSONStudentStorage implements StorageService<Student> {
    private final Path filePath;
    private final Gson gson;

    public JSONStudentStorage(Path filePath) {
        this.filePath = filePath;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public List<Student> loadData() {
        List<Student> students = new ArrayList<>();

        // step 1: check if file is existed
        if (!Files.exists(filePath)) {
            System.err.println("File not found, return empty list: " + filePath.toAbsolutePath());
            return students;
        }

        // step 2: read JSON file
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            // TypeToken is used to tell what type of List
            TypeToken<List<Student>> listType = new TypeToken<List<Student>>() {
            };

            List<Student> importedData = gson.fromJson(reader, listType);

            if (importedData != null) {
                students = importedData;
            }
        } catch (IOException e) {
            System.err.println("Error when reading JSON file: " + e.getMessage());
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

        // step 2: write JSON file
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            System.err.println("Error when writing JSON file: " + e.getMessage());
        }
    }
}
