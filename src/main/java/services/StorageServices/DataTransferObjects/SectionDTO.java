package services.StorageServices.DataTransferObjects;

import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SectionDTO {
    private String id;
    private String sectionCode;
    private String subjectId;
    private int maxCapacity;
    private List<TimeSlotDTO> timeSlots;
    private Set<String> studentIds;
}
