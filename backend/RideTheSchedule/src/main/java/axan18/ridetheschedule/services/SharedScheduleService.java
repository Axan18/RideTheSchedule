package axan18.ridetheschedule.services;

import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.entities.SharedSchedule;
import axan18.ridetheschedule.models.AppUserDTO;
import axan18.ridetheschedule.models.SharedScheduleDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SharedScheduleService {
    SharedSchedule createSharedSchedule(SharedScheduleDTO sharedScheduleDTO);
    List<AppUserDTO> getUsersSharingSchedulesWithUser(UUID userId, int month, int year);
}
