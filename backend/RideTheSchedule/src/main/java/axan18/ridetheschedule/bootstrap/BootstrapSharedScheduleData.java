package axan18.ridetheschedule.bootstrap;

import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.entities.Schedule;
import axan18.ridetheschedule.entities.SharedSchedule;
import axan18.ridetheschedule.repositories.AppUserRepository;
import axan18.ridetheschedule.repositories.ScheduleRepository;
import axan18.ridetheschedule.repositories.SharedScheduleRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Order(4)
public class BootstrapSharedScheduleData implements CommandLineRunner {

    private final SharedScheduleRepository sharedScheduleRepository;
    private final AppUserRepository appUserRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        List<AppUser> users = appUserRepository.findAll();
        List<Schedule> schedules = scheduleRepository.findAll();
        List<SharedSchedule> sharedScheduleList = new ArrayList<>();
        for(int i = 0; i<schedules.size();i+=2){
            for(int j = 0; j<users.size()-1; j++){
                SharedSchedule ss = SharedSchedule.builder()
                        .id(UUID.randomUUID())
                        .schedule(schedules.get(i))
                        .scheduleOwner(users.get(j))
                        .sharedWith(users.get(j+1))
                        .build();
                sharedScheduleList.add(ss);
            }
        }
        for (int j = 0; j < users.size() - 1; j++) {
            SharedSchedule ss = SharedSchedule.builder()
                    .id(UUID.randomUUID())
                    .schedule(schedules.get(schedules.size() - 1)) //last schedule
                    .scheduleOwner(users.get(j))
                    .sharedWith(users.get(users.size() - 1)) // for last user
                    .build();
            sharedScheduleList.add(ss);
        }
        sharedScheduleRepository.saveAll(sharedScheduleList);
    }
}
