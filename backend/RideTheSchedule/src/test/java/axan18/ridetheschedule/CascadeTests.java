package axan18.ridetheschedule;

import axan18.ridetheschedule.bootstrap.BootstrapScheduleData;
import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.entities.Schedule;
import axan18.ridetheschedule.repositories.AppUserRepository;
import axan18.ridetheschedule.repositories.ScheduleRepository;
import axan18.ridetheschedule.RideTheScheduleApplication;

import axan18.ridetheschedule.services.AppUserServiceJPA;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = RideTheScheduleApplication.class)
@Transactional
public class CascadeTests {
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    /*Bootstrapped:
    users: 20
    schedules: 620
    31 schedules per user
    */

    @Test
    void testRemoveAppUser() {
        assertEquals(20, appUserRepository.findAll().size());
        assertEquals(620, scheduleRepository.findAll().size());
        appUserRepository.deleteAll();
        assertEquals(0, appUserRepository.findAll().size());
        assertEquals(0, scheduleRepository.findAll().size());
    }

    @Test
    void testMergeAppUser()
    {
        AppUser appUser = appUserRepository.findAll().get(0);
        Set<Schedule> schedules = appUser.getSchedules();
        appUser.setUsername("NewUsername");
        appUserRepository.save(appUser);
        assertEquals("NewUsername", appUserRepository.findAll().get(0).getUsername());
        for(Schedule schedule : schedules)
        {
            assertEquals("NewUsername", schedule.getAppUser().getUsername());
        }

    }
    @Test
    void testRemoveSchedule()
    {
        //hibernate will remove the deleted schedule from the user's schedules
        Schedule schedule = scheduleRepository.findAll().get(0);
        Schedule schedule2 = scheduleRepository.findAll().get(1);
        AppUser appUser = schedule.getAppUser();
        scheduleRepository.delete(schedule);
        scheduleRepository.delete(schedule2);
        assertEquals(618, scheduleRepository.findAll().size());
        assertEquals(29, appUser.getSchedules().size());
    }
}