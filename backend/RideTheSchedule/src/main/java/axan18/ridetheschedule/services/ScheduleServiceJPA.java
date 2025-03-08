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
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleServiceJPA implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final AppUserRepository appUserRepository;
    @Override
    public Page<ScheduleDTO> getSchedulesForMonth(UUID userID, int month, int year, int page, int size) {
        LocalDate startDate = LocalDate.of(year, month, 1);

        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        return scheduleRepository.findAllByAppUserIdAndDateBetween(
                        userID,
                        Date.valueOf(startDate),
                        Date.valueOf(endDate),
                        PageRequest.of(page, size))
                .map(scheduleMapper::toScheduleDTO);
    }

    @Override
    public Schedule createSchedule(ScheduleDTO s, UUID userId) {
        Optional<AppUser> userOpt = appUserRepository.findById(userId);
        if(userOpt.isEmpty()) throw new EntityNotFoundException("User not found. Id: "+userId);

        AppUser user = userOpt.get();
        Schedule schedule = scheduleMapper.toSchedule(s);
        schedule.setAppUser(user);
        schedule = scheduleRepository.save(schedule);
        //user.getSchedules().add(schedule);
        return schedule;
    }

    @Override
    public Optional<ScheduleDTO> getScheduleForDate(UUID userID, Date date) {
        Optional<Schedule> schedule = scheduleRepository.findByDateAndAppUserId(date, userID);
        return schedule.map(scheduleMapper::toScheduleDTO);
    }
}
