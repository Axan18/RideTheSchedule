package axan18.ridetheschedule.repositories;

import axan18.ridetheschedule.bootstrap.BootstrapScheduleData;
import axan18.ridetheschedule.entities.Schedule;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(BootstrapScheduleData.class)
class ScheduleRepositoryTest {

    @Autowired
    ScheduleRepository scheduleRepository;
    Schedule testSchedule;

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
        Page<Schedule> monthSchedules = scheduleRepository.findAllByDateBetweenOrderByDate(Date.valueOf("2021-01-01"), Date.valueOf("2021-01-31"), null);
        assertNotNull(monthSchedules);
        assertThat(monthSchedules.getContent().size()).isEqualTo(32);//31 days in January + test one
    }
    @Test
    void testAreSchedulesOrdered()
    {
        scheduleRepository.save(Schedule.builder()
                .date(Date.valueOf("2020-01-01"))
                .createdDate(Date.valueOf("2021-01-01"))
                .build());
        scheduleRepository.save(Schedule.builder()
                .date(Date.valueOf("2022-01-01"))
                .createdDate(Date.valueOf("2021-01-01"))
                .build()); //making sure the schedules are not ordered by order of creation
        Page<Schedule> monthSchedules = scheduleRepository.findAllByDateBetweenOrderByDate(Date.valueOf("2021-01-01"), Date.valueOf("2021-01-31"), null);
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
        Schedule schedule = scheduleRepository.findByDate(Date.valueOf("2021-01-11")).orElse(null);
        assertNotNull(schedule);
        assertEquals(Date.valueOf("2021-01-11"), schedule.getDate());
    }
}