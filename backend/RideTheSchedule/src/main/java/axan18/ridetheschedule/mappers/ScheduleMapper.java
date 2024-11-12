package axan18.ridetheschedule.mappers;

import axan18.ridetheschedule.entities.Schedule;
import axan18.ridetheschedule.models.ScheduleDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ScheduleMapper {
    Schedule toSchedule(ScheduleDTO scheduleDTO);
    ScheduleDTO toScheduleDTO(Schedule schedule);
}
