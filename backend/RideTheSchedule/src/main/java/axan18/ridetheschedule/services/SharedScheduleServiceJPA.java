package axan18.ridetheschedule.services;

import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.entities.SharedSchedule;
import axan18.ridetheschedule.mappers.AppUserMapper;
import axan18.ridetheschedule.mappers.SharedScheduleMapper;
import axan18.ridetheschedule.models.AppUserDTO;
import axan18.ridetheschedule.models.AppUserPublicDTO;
import axan18.ridetheschedule.models.SharedScheduleDTO;
import axan18.ridetheschedule.repositories.SharedScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SharedScheduleServiceJPA implements SharedScheduleService {

    private final SharedScheduleRepository sharedScheduleRepository;
    private final SharedScheduleMapper sharedScheduleMapper;
    private final AppUserMapper appUserMapper;

    @Override
    public SharedScheduleDTO createSharedSchedule(SharedScheduleDTO sharedScheduleDTO) {
        SharedSchedule sharedSchedule = sharedScheduleMapper.toSharedSchedule(sharedScheduleDTO);
        return sharedScheduleMapper.toSharedScheduleDTO(sharedScheduleRepository.save(sharedSchedule));
    }

    @Override
    public List<AppUserPublicDTO> getUsersSharingSchedulesWithUser(UUID userId, String date) {
        List<AppUser> sharersList = sharedScheduleRepository.findUsersWhoSharedSchedules(userId, Date.valueOf(date));
        return appUserMapper.toAppUserPublicDTOList(sharersList);
    }
}