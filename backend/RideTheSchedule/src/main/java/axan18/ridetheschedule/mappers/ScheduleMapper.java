package axan18.ridetheschedule.mappers;

import axan18.ridetheschedule.entities.Schedule;
import axan18.ridetheschedule.models.ScheduleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {DateMapper.class})
public interface ScheduleMapper {
    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);
    @Mapping(source = "lastModified", target = "lastModified", qualifiedByName = "LocalDateTimeToTimestamp")
    Schedule toSchedule(ScheduleDTO scheduleDTO);
    @Mapping(source = "lastModified", target = "lastModified", qualifiedByName = "TimestampToLocalDateTime")
    ScheduleDTO toScheduleDTO(Schedule schedule);
}
