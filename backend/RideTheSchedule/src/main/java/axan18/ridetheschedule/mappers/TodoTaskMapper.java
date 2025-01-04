package axan18.ridetheschedule.mappers;

import axan18.ridetheschedule.entities.TodoTask;
import axan18.ridetheschedule.models.TodoTaskDTO;
import org.mapstruct.Mapper;

@Mapper
public interface TodoTaskMapper {
    TodoTask toTodoTask(TodoTaskDTO todoTaskDTO);
    TodoTaskDTO toTodoTaskDTO(TodoTask todoTask);
}
