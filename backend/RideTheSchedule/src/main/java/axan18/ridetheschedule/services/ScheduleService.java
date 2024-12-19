package axan18.ridetheschedule.services;

import axan18.ridetheschedule.models.ScheduleDTO;
import org.springframework.data.domain.Page;

public interface ScheduleService {
    //schedules for given month
    Page<ScheduleDTO> getSchedulesForMonth(int month, int year, int page, int size);
}
