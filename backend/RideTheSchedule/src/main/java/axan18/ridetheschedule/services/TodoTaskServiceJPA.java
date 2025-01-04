package axan18.ridetheschedule.services;

import axan18.ridetheschedule.mappers.TodoTaskMapper;
import axan18.ridetheschedule.models.TodoTaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import axan18.ridetheschedule.repositories.TodoTaskRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TodoTaskServiceJPA implements TodoTaskService {
    private final TodoTaskRepository todoTaskRepository;
    private final TodoTaskMapper todoTaskMapper;

    @Override
    public Page<TodoTaskDTO> getTodoTasksForSchedule(UUID scheduleId, int page, int size) {
        return todoTaskRepository.findAllByScheduleId(scheduleId, PageRequest.of(page, size)).map(todoTaskMapper::toTodoTaskDTO);
    }
}
