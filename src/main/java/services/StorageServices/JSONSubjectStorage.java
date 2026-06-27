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
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import models.Subject;
import services.StorageServices.DataMappers.SubjectMapper;
import services.StorageServices.DataTransferObjects.SubjectDTO;
import services.responses.ErrorType;
import services.responses.Result;
import services.responses.ResultWithData;

public class JSONSubjectStorage implements StorageService<Subject> {
    private final Path filePath;
    private final Gson gson;

    public JSONSubjectStorage(Path filePath) {
        this.filePath = filePath;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public ResultWithData<List<Subject>> loadData() {
        // step 1: check if file is existed
        if (!Files.exists(filePath)) {
            return ResultWithData.success(new ArrayList<>());
        }

        // step 2: read JSON file
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            // TypeToken is used to tell what type of List
            TypeToken<List<SubjectDTO>> listType = new TypeToken<List<SubjectDTO>>() {
            };

            List<SubjectDTO> importedData = gson.fromJson(reader, listType);

            // check null
            if (importedData == null) {
                return ResultWithData.success(new ArrayList<>());
            }

            return ResultWithData.success(importedData.stream()
                    .map(SubjectMapper::toModel) // <- convert from DTO to Model
                    .toList());

        } catch (JsonSyntaxException e) {
            String message = "JSON file is in wrong format: " + e.getMessage();
            return ResultWithData.fail(message, ErrorType.CANNOT_READ);
        } catch (IOException e) {
            String message = "Error when reading JSON file: " + e.getMessage();
            return ResultWithData.fail(message, ErrorType.SYSTEM_ERROR);
        }
    }

    @Override
    public Result saveData(List<Subject> data) {
        // step 1: make sure the storage folder is existed
        try {
            if (filePath.getParent() != null) {
                Files.createDirectories(filePath.getParent());
            }
        } catch (IOException e) {
            String message = "Error when creating storage folder: " + e.getMessage();
            return Result.fail(message, ErrorType.SYSTEM_ERROR);
        }

        // step 2: write JSON file
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            List<SubjectDTO> exportedData = data.stream()
                    .map(SubjectMapper::toDTO) // <- convert from Model to DTO
                    .toList();

            gson.toJson(exportedData, writer);
            return Result.success();

        } catch (IOException e) {
            String message = "Error when writing JSON file: " + e.getMessage();
            return Result.fail(message, ErrorType.CANNOT_WRITE);
        }
    }
}
