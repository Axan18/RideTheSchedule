package axan18.ridetheschedule.services;

import axan18.ridetheschedule.entities.SharedSchedule;
import axan18.ridetheschedule.mappers.SharedScheduleMapper;
import axan18.ridetheschedule.models.SharedScheduleDTO;
import axan18.ridetheschedule.repositories.SharedScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SharedScheduleServiceImplJPA implements SharedScheduleService {

    private final SharedScheduleRepository sharedScheduleRepository;
    private final SharedScheduleMapper sharedScheduleMapper;

    @Override
    public SharedSchedule createSharedSchedule(SharedScheduleDTO sharedScheduleDTO) {
        SharedSchedule sharedSchedule = sharedScheduleMapper.toSharedSchedule(sharedScheduleDTO);
        return sharedScheduleRepository.save(sharedSchedule);
    }
}
