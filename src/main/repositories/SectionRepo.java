package main.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import main.models.Section;

public class SectionRepo {
    private final Map<String, Section> sectionMap;
    private static SectionRepo instance; // singleton

    public SectionRepo() {
        this.sectionMap = new ConcurrentHashMap<>();
    }

    public List<Section> getSectionList() {
        return new ArrayList<>(sectionMap.values());
    }

    public static SectionRepo getInstance() {
        if (instance == null) {
            instance = new SectionRepo();
        }
        return instance;
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
}
