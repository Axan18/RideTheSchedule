package axan18.ridetheschedule.services;

import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.entities.Schedule;
import axan18.ridetheschedule.mappers.ScheduleMapper;
import axan18.ridetheschedule.models.ScheduleDTO;
import axan18.ridetheschedule.repositories.AppUserRepository;
import axan18.ridetheschedule.repositories.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleServiceJPA implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final AppUserRepository appUserRepository;
    @Override
    public Page<ScheduleDTO> getSchedulesForMonth(int month, int year, int page, int size) {
        return scheduleRepository.findAllByDateBetween(
                Date.valueOf(year + "-" + month + "-01"),
                Date.valueOf(year + "-" + month + "-31"),
                PageRequest.of(page, size)).map(scheduleMapper::toScheduleDTO);
    }

    @Override
    public Schedule createSchedule(ScheduleDTO s, UUID userId) {
        Optional<AppUser> userOpt = appUserRepository.findById(userId);
        if(userOpt.isEmpty()) throw new EntityNotFoundException("User not found. Id: "+userId);

        AppUser user = userOpt.get();
        Schedule schedule = scheduleMapper.toSchedule(s);
        schedule.setAppUser(user);
        schedule = scheduleRepository.save(schedule);
        user.getSchedules().add(schedule);
        return schedule;
    }
}
