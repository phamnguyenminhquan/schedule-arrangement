package main.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import main.models.Section;

public class SectionRepo {
    private final List<Section> sections;
    private static SectionRepo instance; // singleton

    public SectionRepo() {
        this.sections = new ArrayList<>();
    }

    public List<Section> getSections() {
        return sections;
    }

    public static SectionRepo getInstance() {
        if (instance == null) {
            instance = new SectionRepo();
        }
        return instance;
    }

    public Section findById(String sectionId) {
        return this.sections.stream()
                .filter((section) -> section.getId().equals(sectionId))
                .findAny()
                .orElse(null);
    }

    public List<Section> findAllById(Set<String> sectionIds) {
        return this.sections.stream()
                .filter((section) -> sectionIds.contains(section.getId()))
                .collect(Collectors.toList());
    }
}
