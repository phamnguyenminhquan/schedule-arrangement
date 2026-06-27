package repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import models.Section;
import services.StorageServices.StorageService;
import services.responses.ErrorType;
import services.responses.Result;
import services.responses.ResultWithData;

public class SectionRepo {
    private final AtomicBoolean isInitialized;

    private final Map<String, Section> sectionMap;
    private StorageService<Section> sectionStorage;

    private SectionRepo() {
        this.sectionMap = new ConcurrentHashMap<>();
        this.isInitialized = new AtomicBoolean(false);
    }

    public List<Section> getSectionList() {
        return new ArrayList<>(sectionMap.values());
    }

    // singleton
    private static class SingletonHelper {
        private static final SectionRepo INSTANCE = new SectionRepo();
    }

    public static SectionRepo getInstance() {
        return SingletonHelper.INSTANCE;
    }

    /**
     * Initializes the repository with the given storage service
     * 
     * @param sectionStorage the storage service to use
     * @throws IllegalStateException    if the repository has already been
     *                                  initialized
     * @throws IllegalArgumentException if sectionStorage is null
     */
    public void initialize(StorageService<Section> sectionStorage) {
        // atomically flip from false to true, if already true => throw exception
        if (!isInitialized.compareAndSet(false, true)) {
            throw new IllegalStateException("SectionRepo has already initialized the storage");
        }
        if (sectionStorage == null) {
            isInitialized.set(false);
            throw new IllegalArgumentException("StorageService cannot be null");
        }
        this.sectionStorage = sectionStorage;
    }

    public Section findById(String sectionId) {
        return sectionMap.get(sectionId);
    }

    public List<Section> findAllById(Set<String> sectionIds) {
        return sectionIds.stream()
                .map(sectionMap::get)
                .filter(Objects::nonNull)
                .toList();
    }

    public void add(Section section) {
        if (section != null && !sectionMap.containsKey(section.getId())) {
            sectionMap.put(section.getId(), section);
        }
    }

    public Result load() {
        // step 1: check if storage initialized
        if (sectionStorage == null) {
            String message = "SectionRepo has not initialized the StorageService yet";
            return Result.fail(message, ErrorType.SYSTEM_ERROR);
        }

        // step 2: get result and check valid
        ResultWithData<List<Section>> storageResult = sectionStorage.loadData();

        if (!storageResult.isValid()) {
            return Result.fail(storageResult.getMessage(), storageResult.getErrorType());
        }

        // step 3: update data
        List<Section> sections = storageResult.getData();
        for (Section section : sections) {
            sectionMap.put(section.getId(), section);
        }

        return Result.success();
    }

    public Result save() {
        // step 1: check if storage initialized
        if (sectionStorage == null) {
            String message = "SectionRepo has not initialized the StorageService yet";
            return Result.fail(message, ErrorType.SYSTEM_ERROR);
        }

        // step 2: return result
        return sectionStorage.saveData(getSectionList());
    }
}
