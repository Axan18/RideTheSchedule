package axan18.ridetheschedule.services;

import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.entities.SharedSchedule;
import axan18.ridetheschedule.mappers.AppUserMapper;
import axan18.ridetheschedule.mappers.SharedScheduleMapper;
import axan18.ridetheschedule.models.AppUserDTO;
import axan18.ridetheschedule.models.SharedScheduleDTO;
import axan18.ridetheschedule.repositories.SharedScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SharedScheduleServiceJPA implements SharedScheduleService {

    private final SharedScheduleRepository sharedScheduleRepository;
    private final SharedScheduleMapper sharedScheduleMapper;
    private final AppUserMapper appUserMapper;

    @Override
    public SharedSchedule createSharedSchedule(SharedScheduleDTO sharedScheduleDTO) {
        SharedSchedule sharedSchedule = sharedScheduleMapper.toSharedSchedule(sharedScheduleDTO);
        return sharedScheduleRepository.save(sharedSchedule);
    }

    @Override
    public List<AppUserDTO> getUsersSharingSchedulesWithUser(UUID userId, int month, int year) {
        List<AppUser> sharersList = sharedScheduleRepository.findUsersWhoSharedSchedules(userId,month,year);
        return sharersList.stream().map(appUserMapper::toAppUserDTO).toList();
    }
}