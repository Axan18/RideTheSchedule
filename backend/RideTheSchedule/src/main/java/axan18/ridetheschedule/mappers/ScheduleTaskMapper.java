package axan18.ridetheschedule.mappers;

import axan18.ridetheschedule.entities.ScheduleTask;
import axan18.ridetheschedule.models.ScheduleTaskDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ScheduleTaskMapper {
    ScheduleTask toScheduleTask(ScheduleTaskDTO scheduleTaskDTO);
    ScheduleTaskDTO toScheduleTaskDTO(ScheduleTask scheduleTask);
}
