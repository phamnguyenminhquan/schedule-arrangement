package services.StorageServices.DataMappers;

import models.Subject;
import services.StorageServices.DataTransferObjects.SubjectDTO;

public class SubjectMapper {
    public static Subject toModel(SubjectDTO dto) {
        return Subject.builder()
                .id(dto.getId())
                .name(dto.getName())
                .noCredits(dto.getNoCredits())
                .build();
    }

    public static SubjectDTO toDTO(Subject subject) {
        SubjectDTO dto = new SubjectDTO();
        dto.setId(subject.getId());
        dto.setName(subject.getName());
        dto.setNoCredits(subject.getNoCredits());
        return dto;
    }
}
