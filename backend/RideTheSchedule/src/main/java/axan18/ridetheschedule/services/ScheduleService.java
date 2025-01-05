package axan18.ridetheschedule.services;

import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.entities.Schedule;
import axan18.ridetheschedule.models.ScheduleDTO;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface ScheduleService {
    Page<ScheduleDTO> getSchedulesForMonth(UUID scheduleId, int month, int year, int page, int size);
    Schedule createSchedule(ScheduleDTO schedule, UUID userId);
}
