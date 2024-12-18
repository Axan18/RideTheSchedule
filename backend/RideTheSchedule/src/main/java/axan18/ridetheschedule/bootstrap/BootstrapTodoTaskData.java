package axan18.ridetheschedule.bootstrap;

import axan18.ridetheschedule.entities.Schedule;
import axan18.ridetheschedule.entities.TodoTask;
import axan18.ridetheschedule.repositories.ScheduleRepository;
import axan18.ridetheschedule.repositories.TodoTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Order(3)
public class BootstrapTodoTaskData implements CommandLineRunner {

    private final TodoTaskRepository todoTaskRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        loadTodoTaskData();
    }

    private void loadTodoTaskData() {
        Schedule schedule = scheduleRepository.findAll().get(0);
        TodoTask todoTask = TodoTask.builder()
                .name("Test Task 0")
                .description("Test Task")
                .schedule(schedule)
                .build();
        TodoTask todoTask1 = TodoTask.builder()
                .name("Test Task 1")
                .description("Test Task 1")
                .schedule(schedule)
                .build();
        todoTask.setSchedule(schedule);
        todoTask1.setSchedule(schedule);
        todoTaskRepository.save(todoTask);
        todoTaskRepository.save(todoTask1);

    }
}
