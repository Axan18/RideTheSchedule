package axan18.ridetheschedule.services;

import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.entities.Schedule;
import axan18.ridetheschedule.entities.ScheduleTask;
import axan18.ridetheschedule.mappers.ScheduleTaskMapper;
import axan18.ridetheschedule.models.ScheduleTaskDTO;
import axan18.ridetheschedule.repositories.AppUserRepository;
import axan18.ridetheschedule.repositories.ScheduleRepository;
import axan18.ridetheschedule.repositories.ScheduleTaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ScheduleTaskServiceJPA implements ScheduleTaskService {
    private final ScheduleTaskRepository scheduleTaskRepository;
    private final AppUserRepository appUserRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleTaskMapper scheduleTaskMapper;

    @Override
    public Optional<ScheduleTaskDTO> createScheduleTask(UUID userID, ScheduleTaskDTO taskDTO) {
        ScheduleTask task = scheduleTaskMapper.toScheduleTask(taskDTO);
        LocalDateTime startDate =taskDTO.getStartTime();
        Date date = Date.valueOf(startDate.toLocalDate());
        Optional<Schedule> scheduleOpt = scheduleRepository.findByDateAndAppUserId(date,userID);
        AppUser user = appUserRepository.findById(userID)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Schedule schedule;
        schedule = scheduleOpt.orElseGet(() -> scheduleRepository.save(Schedule.builder()
                .appUser(user)
                .date(date)
                .createdDate(Date.valueOf(LocalDate.now()))
                .lastModified(Timestamp.valueOf(LocalDateTime.now()))
                .build()));

        task.setSchedule(schedule);
        ScheduleTask newTask = scheduleTaskRepository.save(task);
        return Optional.of(scheduleTaskMapper.toScheduleTaskDTO(newTask));
    }

    @Override
    public List<ScheduleTaskDTO> getTasksForSchedule(UUID scheduleID) {
        List<ScheduleTask> tasks = scheduleTaskRepository.findAllByScheduleId(scheduleID);
        return scheduleTaskMapper.toDTOList(tasks);
    }
}
