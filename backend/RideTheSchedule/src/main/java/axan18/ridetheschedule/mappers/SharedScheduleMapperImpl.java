package axan18.ridetheschedule.mappers;

import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.entities.Schedule;
import axan18.ridetheschedule.entities.SharedSchedule;
import axan18.ridetheschedule.models.SharedScheduleDTO;
import axan18.ridetheschedule.repositories.AppUserRepository;
import axan18.ridetheschedule.repositories.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SharedScheduleMapperImpl implements SharedScheduleMapper {
    private final ScheduleRepository scheduleRepository;
    private final AppUserRepository appUserRepository;

    @Override
    public SharedSchedule toSharedSchedule(SharedScheduleDTO sharedScheduleDTO) {
        if (sharedScheduleDTO == null) {
            return null;
        }

        SharedSchedule.SharedScheduleBuilder sharedSchedule = SharedSchedule.builder()
                .id(sharedScheduleDTO.getId());

        if (sharedScheduleDTO.getScheduleId() != null) {
            sharedSchedule.schedule(scheduleRepository.findById(sharedScheduleDTO.getScheduleId()).orElse(null));
        }
        if (sharedScheduleDTO.getOwnerId() != null) {
            sharedSchedule.scheduleOwner(appUserRepository.findById(sharedScheduleDTO.getOwnerId()).orElse(null));
        }
        if (sharedScheduleDTO.getSharedWithId() != null) {
            sharedSchedule.sharedWith(appUserRepository.findById(sharedScheduleDTO.getSharedWithId()).orElse(null));
        }

        return sharedSchedule.build();
    }
    @Override
    public SharedScheduleDTO toSharedScheduleDTO(SharedSchedule sharedSchedule) {
        if (sharedSchedule == null) {
            return null;
        }

        return SharedScheduleDTO.builder()
                .id(sharedSchedule.getId())
                .scheduleId(sharedSchedule.getSchedule() != null ? sharedSchedule.getSchedule().getId() : null)
                .ownerId(sharedSchedule.getScheduleOwner() != null ? sharedSchedule.getScheduleOwner().getId() : null)
                .sharedWithId(sharedSchedule.getSharedWith() != null ? sharedSchedule.getSharedWith().getId() : null)
                .build();
    }

}
