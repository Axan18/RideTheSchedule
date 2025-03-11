package axan18.ridetheschedule.mappers;

import axan18.ridetheschedule.entities.SharedSchedule;
import axan18.ridetheschedule.models.SharedScheduleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

public interface SharedScheduleMapper {
    SharedSchedule toSharedSchedule(SharedScheduleDTO sharedScheduleDTO);
    SharedScheduleDTO toSharedScheduleDTO(SharedSchedule sharedSchedule);
}
