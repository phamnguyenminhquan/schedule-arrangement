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

    private SectionRepo() {
        this.sectionMap = new ConcurrentHashMap<>();
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
}
