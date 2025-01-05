package axan18.ridetheschedule.services;

import axan18.ridetheschedule.entities.TodoTask;
import axan18.ridetheschedule.models.TodoTaskDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface TodoTaskService{
    Page<TodoTaskDTO> getTodoTasksForSchedule(UUID scheduleId, int page, int size);
    TodoTask createTodoTask(TodoTaskDTO task, UUID scheduleId);
}
