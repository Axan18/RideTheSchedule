package axan18.ridetheschedule.bootstrap;

import axan18.ridetheschedule.entities.Schedule;
import axan18.ridetheschedule.repositories.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
@RequiredArgsConstructor
public class BootstrapScheduleData implements CommandLineRunner {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public void run(String... args) throws Exception {
        loadScheduleData();
    }
    private void loadScheduleData() {
        for(int i = 1; i <= 31; i++) {
             Schedule schedule = Schedule.builder()
                     .date(i<10?Date.valueOf("2021-01-0"+i):Date.valueOf("2021-01-"+i))
                     .createdDate(Date.valueOf("2021-01-01"))
                     .build();
             scheduleRepository.save(schedule);
        }
    }
}
