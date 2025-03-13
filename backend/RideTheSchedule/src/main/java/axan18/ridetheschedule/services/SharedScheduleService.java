package axan18.ridetheschedule.services;

import axan18.ridetheschedule.models.AppUserPublicDTO;
import axan18.ridetheschedule.models.SharedScheduleDTO;
import java.util.List;
import java.util.UUID;

public interface SharedScheduleService {
    SharedScheduleDTO createSharedSchedule(SharedScheduleDTO sharedScheduleDTO);
    List<AppUserPublicDTO> getUsersSharingSchedulesWithUser(UUID userId, String date);
}
