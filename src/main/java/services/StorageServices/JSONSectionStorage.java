package services.StorageServices;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import models.Section;

public class JSONSectionStorage implements StorageService<Section> {
    private final Path filePath;
    private final Gson gson;

    public JSONSectionStorage(Path filePath) {
        this.filePath = filePath;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public List<Section> loadData() {
        List<Section> sections = new ArrayList<>();

        // step 1: check if file is existed
        if (!Files.exists(filePath)) {
            System.err.println("File not found, return empty list: " + filePath.toAbsolutePath());
            return sections;
        }

        // step 2: initialize bufferedReader with try-with-resources
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            Type listType = new TypeToken<ArrayList<Section>>() {
            }.getType();

            List<Section> importedData = gson.fromJson(reader, listType);

            if (importedData != null) {
                sections = importedData;
            }
        } catch (IOException e) {
            System.err.println("Error when reading JSON Section file: " + e.getMessage());
        }
        return sections;
    }

    @Override
    public void saveData(List<Section> data) {
        // step 1: make sure the storage folder is existed
        try {
            if (filePath.getParent() != null) {
                Files.createDirectories(filePath.getParent());
            }
        } catch (IOException e) {
            System.err.println("Error when creating storage folder: " + e.getMessage());
            return;
        }

        // 2. Sử dụng BufferedWriter kết hợp với Gson để đẩy toàn bộ danh sách xuống
        // file trong 1 dòng lệnh
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            System.err.println("Error when writing JSON Section file: " + e.getMessage());
        }
    }
}
