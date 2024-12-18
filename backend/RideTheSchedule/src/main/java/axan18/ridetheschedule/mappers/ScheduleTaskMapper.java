package axan18.ridetheschedule.mappers;

import axan18.ridetheschedule.entities.ScheduleTask;
import axan18.ridetheschedule.models.ScheduleTaskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = DateMapper.class, componentModel = "spring")
public interface ScheduleTaskMapper {
    ScheduleTaskMapper INSTANCE = Mappers.getMapper(ScheduleTaskMapper.class);
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "LocalDateTimeToTimestamp")
    @Mapping(source = "lastModified", target = "lastModified", qualifiedByName = "LocalDateTimeToTimestamp")
    ScheduleTask toScheduleTask(ScheduleTaskDTO scheduleTaskDTO);
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "TimestampToLocalDateTime")
    @Mapping(source = "lastModified", target = "lastModified", qualifiedByName = "TimestampToLocalDateTime")
    ScheduleTaskDTO toScheduleTaskDTO(ScheduleTask scheduleTask);

}
