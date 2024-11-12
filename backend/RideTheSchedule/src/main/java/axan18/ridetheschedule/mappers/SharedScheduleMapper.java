package axan18.ridetheschedule.mappers;

import axan18.ridetheschedule.entities.SharedSchedule;
import axan18.ridetheschedule.models.SharedScheduleDTO;
import org.mapstruct.Mapper;

@Mapper
public interface SharedScheduleMapper {
    SharedSchedule toSharedSchedule(SharedScheduleDTO sharedScheduleDTO);
    SharedScheduleDTO toSharedScheduleDTO(SharedSchedule sharedSchedule);
}
