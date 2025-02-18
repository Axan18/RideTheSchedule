package axan18.ridetheschedule.services;

import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.entities.Schedule;
import axan18.ridetheschedule.entities.TodoTask;
import axan18.ridetheschedule.mappers.TodoTaskMapper;
import axan18.ridetheschedule.models.TodoTaskDTO;
import axan18.ridetheschedule.repositories.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import axan18.ridetheschedule.repositories.TodoTaskRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TodoTaskServiceJPA implements TodoTaskService {
    private final TodoTaskRepository todoTaskRepository;
    private final TodoTaskMapper todoTaskMapper;
    private final ScheduleRepository scheduleRepository;

    @Override
    public Page<TodoTaskDTO> getTodoTasksForSchedule(UUID scheduleId, int page, int size) {
        return todoTaskRepository.findAllByScheduleId(scheduleId, PageRequest.of(page, size)).map(todoTaskMapper::toTodoTaskDTO);
    }

    @Override
    public TodoTask createTodoTask(TodoTaskDTO task, UUID scheduleId) {
        TodoTask todoTask = todoTaskMapper.toTodoTask(task);
        todoTask.setScheduleId(scheduleId);
        todoTask = todoTaskRepository.save(todoTask);
        Optional<Schedule> schedule = scheduleRepository.findById(scheduleId);
        if (schedule.isEmpty()) throw new EntityNotFoundException("Not found: schedule ID = "+scheduleId);

        Schedule s = schedule.get();
        s.getTodoTasks().add(todoTask); //neccessary as it is unidirectional relationship
        scheduleRepository.save(s);

        return todoTask;
    }
}
