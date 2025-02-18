package axan18.ridetheschedule.services;

import axan18.ridetheschedule.entities.SharedSchedule;
import axan18.ridetheschedule.models.SharedScheduleDTO;

import java.util.Optional;

public interface SharedScheduleService {
    SharedSchedule createSharedSchedule(SharedScheduleDTO sharedScheduleDTO);
}
