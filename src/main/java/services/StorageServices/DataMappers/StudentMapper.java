package services.StorageServices.DataMappers;

import java.util.HashSet;

import models.Student;
import services.StorageServices.DataTransferObjects.StudentDTO;

public class StudentMapper {
    public static Student toModel(StudentDTO dto) {
        return Student.builder()
                .id(dto.getId())
                .name(dto.getName())
                .sectionIds(dto.getSectionIds() != null
                        ? dto.getSectionIds()
                        : new HashSet<>())
                .build();
    }

    public static StudentDTO toDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setSectionIds(student.getSectionIds());
        return dto;
    }
}
