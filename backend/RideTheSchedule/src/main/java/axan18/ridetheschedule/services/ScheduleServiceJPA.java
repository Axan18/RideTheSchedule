package axan18.ridetheschedule.services;

import axan18.ridetheschedule.entities.Schedule;
import axan18.ridetheschedule.mappers.ScheduleMapper;
import axan18.ridetheschedule.models.ScheduleDTO;
import axan18.ridetheschedule.repositories.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
@RequiredArgsConstructor
public class ScheduleServiceJPA implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    @Override
    public Page<ScheduleDTO> getSchedulesForMonth(int month, int year, int page, int size) {
        return scheduleRepository.findAllByDateBetween(
                Date.valueOf(year + "-" + month + "-01"),
                Date.valueOf(year + "-" + month + "-31"),
                PageRequest.of(page, size)).map(scheduleMapper::toScheduleDTO);
    }

}
