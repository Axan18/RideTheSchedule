package axan18.ridetheschedule.repositories;

import axan18.ridetheschedule.bootstrap.BootstrapScheduleData;
import axan18.ridetheschedule.bootstrap.BootstrapUserData;
import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.entities.Schedule;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({BootstrapUserData.class, BootstrapScheduleData.class})
class ScheduleRepositoryTest {

    @Autowired
    ScheduleRepository scheduleRepository;
    Schedule testSchedule;
    @Autowired
    private AppUserRepository appUserRepository;

    @BeforeEach
    void setUp() {
        testSchedule = scheduleRepository.save(Schedule.builder()
                .date(Date.valueOf("2021-01-01"))
                .createdDate(Date.valueOf("2021-01-01"))
                .build());
    }

    @Test
    void testSaveSchedule()
    {
        Schedule schedule = scheduleRepository.save(testSchedule);
        scheduleRepository.flush();
        assertNotNull(schedule);
        assertNotNull(schedule.getId());
    }
    @Test
    void testSaveScheduleWithNull()
    {
        assertThrows(ConstraintViolationException.class, () ->{
            scheduleRepository.save(Schedule.builder()
                    .date(null)
                    .createdDate(null)
                    .build());
            scheduleRepository.flush();
        });
    }
    @Test
    void testDeleteSchedule()
    {
        Schedule schedule = scheduleRepository.save(testSchedule);
        scheduleRepository.delete(schedule);
        scheduleRepository.flush();
        assertFalse(scheduleRepository.existsById(schedule.getId()));
    }
    @Test
    void testGetMonthSchedule()
    {
        AppUser user = appUserRepository.findAll().get(0);
        Page<Schedule> monthSchedules = scheduleRepository.findAllByAppUserIdAndDateBetween(user.getId(), Date.valueOf("2021-01-01"), Date.valueOf("2021-01-31"), null);
        assertNotNull(monthSchedules);
        assertThat(monthSchedules.getContent().size()).isEqualTo(31);//31 days in January
    }
    @Test
    void testAreSchedulesOrdered()
    {
        UUID userId = appUserRepository.findAll().get(0).getId();
        scheduleRepository.save(Schedule.builder()
                .date(Date.valueOf("2020-01-01"))
                .createdDate(Date.valueOf("2021-01-01"))
                .build());
        scheduleRepository.save(Schedule.builder()
                .date(Date.valueOf("2022-01-01"))
                .createdDate(Date.valueOf("2021-01-01"))
                .build()); //making sure the schedules are not ordered by order of creation
        Page<Schedule> monthSchedules = scheduleRepository.findAllByAppUserIdAndDateBetweenOrderByDate(
                userId, Date.valueOf("2021-01-01"), Date.valueOf("2021-01-31"), null);
        assertNotNull(monthSchedules);
        Date previousDate = Date.valueOf("2021-01-01");
        for(Schedule schedule: monthSchedules)
        {
            assertTrue(schedule.getDate().compareTo(previousDate) >= 0);
            previousDate = schedule.getDate();
        }
    }
    @Test
    void testGetScheduleByDate()
    {
        UUID userId = appUserRepository.findAll().get(0).getId();
        Optional<Schedule> s = scheduleRepository.findByDateAndAppUserId(Date.valueOf("2021-01-11"),userId);
        assertTrue(s.isPresent());
        Schedule schedule = s.get();
        assertEquals(Date.valueOf("2021-01-11"), schedule.getDate());
    }
}