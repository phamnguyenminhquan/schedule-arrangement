package services.StorageServices.DataMappers;

import models.TimeSlot;
import services.StorageServices.DataTransferObjects.TimeSlotDTO;

public class TimeSlotMapper {
    public static TimeSlot toModel(TimeSlotDTO dto) {
        return TimeSlot.builder()
                .dayOfWeek(dto.getDayOfWeek())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();
    }

    public static TimeSlotDTO toDTO(TimeSlot timeSlot) {
        TimeSlotDTO dto = new TimeSlotDTO();
        dto.setDayOfWeek(timeSlot.getDayOfWeek());
        dto.setStartTime(timeSlot.getStartTime());
        dto.setEndTime(timeSlot.getEndTime());
        return dto;
    }
}
