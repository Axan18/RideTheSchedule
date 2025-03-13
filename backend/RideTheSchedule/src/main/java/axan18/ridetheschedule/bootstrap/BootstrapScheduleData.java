package axan18.ridetheschedule.bootstrap;

import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.entities.Schedule;
import axan18.ridetheschedule.repositories.AppUserRepository;
import axan18.ridetheschedule.repositories.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Order(2)
public class BootstrapScheduleData implements CommandLineRunner {

    private final ScheduleRepository scheduleRepository;
    private final AppUserRepository appUserRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if(scheduleRepository.count()==0) loadScheduleData();
    }
    private void loadScheduleData() {
        List<AppUser> users = appUserRepository.findAll();
        for(AppUser user: users) {
            Set<Schedule> schedules = new HashSet<>();
            for(int i = 1; i <= 31; i++) {
                Schedule schedule = Schedule.builder()
                        .date(i<10?Date.valueOf("2021-01-0"+i):Date.valueOf("2021-01-"+i))
                        .createdDate(Date.valueOf("2021-01-01"))
                        .build();
                user.getSchedules().add(schedule);
                schedule.setAppUser(user);
                schedules.add(schedule);
            }
            scheduleRepository.saveAll(schedules);
        }
    }
}
