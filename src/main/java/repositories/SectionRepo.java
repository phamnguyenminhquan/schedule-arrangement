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

public class SectionRepo {
    private final Map<String, Section> sectionMap;
    private StorageService<Section> sectionStorage;
    private final AtomicBoolean isInitialized;

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

    public SectionRepo initialize(StorageService<Section> sectionStorage) {
        // atomically flip from false to true, if already true => throw exception
        if (!isInitialized.compareAndSet(false, true)) {
            throw new IllegalStateException("SectionRepo has already initialized the storage");
        }

        // guarantee non-null input
        if (sectionStorage == null) {
            isInitialized.set(false);
            throw new IllegalArgumentException("StorageService cannot be null");
        }
        this.sectionStorage = sectionStorage;
        return this;
    }

    public void load() {
        if (sectionStorage == null) {
            throw new IllegalStateException("SectionRepo has not initialized the storage yet");
        }
        List<Section> sections = sectionStorage.loadData();
        for (Section section : sections) {
            sectionMap.put(section.getId(), section);
        }
    }

    public void save() {
        if (sectionStorage == null) {
            throw new IllegalStateException("SectionRepo has not initialized the storage yet");
        }
        sectionStorage.saveData(getSectionList());
    }
}
