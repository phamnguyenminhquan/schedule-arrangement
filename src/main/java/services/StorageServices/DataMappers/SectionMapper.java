package services.StorageServices.DataMappers;

import java.util.HashSet;

import models.Section;
import services.StorageServices.DataTransferObjects.SectionDTO;

public class SectionMapper {
    public static Section toModel(SectionDTO dto) {
        return Section.builder()
                .id(dto.getId())
                .sectionCode(dto.getSectionCode())
                .subjectId(dto.getSubjectId())
                .maxCapacity(dto.getMaxCapacity())
                .timeSlots(dto.getTimeSlots().stream()
                        .map(TimeSlotMapper::toModel)
                        .toList())
                .studentIds(dto.getStudentIds() != null
                        ? dto.getStudentIds()
                        : new HashSet<>())
                .build();
    }

    public static SectionDTO toDTO(Section section) {
        SectionDTO dto = new SectionDTO();
        dto.setId(section.getId());
        dto.setSectionCode(section.getSectionCode());
        dto.setSubjectId(section.getSubjectId());
        dto.setTimeSlots(section.getTimeSlots().stream()
                .map(TimeSlotMapper::toDTO)
                .toList());
        dto.setStudentIds(section.getStudentIds());
        return dto;
    }
}
