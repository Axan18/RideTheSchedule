package axan18.ridetheschedule.services;

import axan18.ridetheschedule.models.ScheduleTaskDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ScheduleTaskService {
    Optional<ScheduleTaskDTO> createScheduleTask(UUID id, ScheduleTaskDTO taskDTO);
    List<ScheduleTaskDTO> getTasksForSchedule(UUID scheduleID);
}
