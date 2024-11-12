package axan18.ridetheschedule.mappers;

import axan18.ridetheschedule.entities.RecurringTask;
import axan18.ridetheschedule.models.RecurringTaskDTO;
import org.mapstruct.Mapper;

@Mapper
public interface RecurringTaskMapper {
    RecurringTask toRecurringTask(RecurringTaskDTO recurringTaskDTO);
    RecurringTaskDTO toRecurringTaskDTO(RecurringTask recurringTask);
    
}
