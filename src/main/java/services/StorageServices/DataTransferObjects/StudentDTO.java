package services.StorageServices.DataTransferObjects;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentDTO {
    private String id;
    private String name;
    private Set<String> sectionIds;
}
