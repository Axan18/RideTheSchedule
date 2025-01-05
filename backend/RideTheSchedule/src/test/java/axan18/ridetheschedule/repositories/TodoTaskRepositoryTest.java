package axan18.ridetheschedule.repositories;

import axan18.ridetheschedule.bootstrap.BootstrapScheduleData;
import axan18.ridetheschedule.bootstrap.BootstrapUserData;
import axan18.ridetheschedule.entities.Schedule;
import axan18.ridetheschedule.entities.TodoTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest

class TodoTaskRepositoryTest {
    @Autowired
    TodoTaskRepository todoTaskRepository;
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
    void testGetAllTodoTasksForSchedule() {
        TodoTask todoTask1 = TodoTask.builder()
                .name("Task 1")
                .description("Task 1 description")
                .completed(false)
                .scheduleId(testSchedule.getId())
                .build();
        TodoTask todoTask2 = TodoTask.builder()
                .name("Task 2")
                .description("Task 2 description")
                .completed(false)
                .scheduleId(testSchedule.getId())
                .build();
        todoTaskRepository.save(todoTask1);
        todoTaskRepository.save(todoTask2);
        todoTaskRepository.flush();
        assertEquals(2,  todoTaskRepository.findAllByScheduleId(testSchedule.getId(), null).getTotalElements());
    }

}