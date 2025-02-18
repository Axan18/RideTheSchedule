package axan18.ridetheschedule.services;

import axan18.ridetheschedule.entities.Schedule;
import axan18.ridetheschedule.entities.TodoTask;
import axan18.ridetheschedule.mappers.TodoTaskMapper;
import axan18.ridetheschedule.models.TodoTaskDTO;
import axan18.ridetheschedule.repositories.ScheduleRepository;
import axan18.ridetheschedule.repositories.TodoTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TodoTaskServiceJPATest {
    @Mock
    private TodoTaskMapper todoTaskMapper;
    @Mock
    private TodoTaskRepository todoTaskRepository;
    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    TodoTaskServiceJPA todoTaskServiceJPA;

    int page = 0;
    int size = 10;
    PageRequest pageRequest;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pageRequest = PageRequest.of(page, size);
    }

    @Test
    void testGetTodoTasksForSchedule() {
        UUID scheduleId = UUID.randomUUID();
        UUID todoTaskId1 = UUID.randomUUID();
        UUID todoTaskId2 = UUID.randomUUID();

        Schedule schedule = Schedule.builder()
                .date(Date.valueOf("2021-01-10"))
                .createdDate(Date.valueOf("2021-01-01"))
                .build();

        List<TodoTask> tasks = List.of(
                TodoTask.builder().id(todoTaskId1).description("Task1 desc").name("task1").build(),
                TodoTask.builder().id(todoTaskId2).description("Task2 desc").name("task2").build()
        );

        when(todoTaskRepository.findAllByScheduleId(scheduleId, pageRequest)).thenReturn(new PageImpl<>(tasks));

        TodoTaskDTO dto1 = TodoTaskDTO.builder().id(todoTaskId1).description("Task1 desc").name("task1").build();
        TodoTaskDTO dto2 = TodoTaskDTO.builder().id(todoTaskId2).description("Task2 desc").name("task2").build();

        when(todoTaskMapper.toTodoTaskDTO(tasks.get(0))).thenReturn(dto1);
        when(todoTaskMapper.toTodoTaskDTO(tasks.get(1))).thenReturn(dto2);

        Page<TodoTaskDTO> result = todoTaskServiceJPA.getTodoTasksForSchedule(scheduleId, page, size);

        assertEquals(2, result.getTotalElements());
        assertEquals(dto1, result.getContent().get(0));
        assertEquals(dto2, result.getContent().get(1));

        verify(todoTaskRepository).findAllByScheduleId(scheduleId, pageRequest);
        verify(todoTaskMapper).toTodoTaskDTO(tasks.get(0));
        verify(todoTaskMapper).toTodoTaskDTO(tasks.get(1));
    }

    @Test
    void testCreateTodoTask()
    {
        UUID scheduleId = UUID.randomUUID();
        Schedule schedule = Schedule.builder().id(scheduleId).build();
        TodoTaskDTO todoTaskDTO = TodoTaskDTO.builder().build();
        TodoTask todoTask = TodoTask.builder().build();
        when(todoTaskMapper.toTodoTask(todoTaskDTO)).thenReturn(todoTask);
        when(todoTaskRepository.save(todoTask)).thenReturn(todoTask);
        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));

        TodoTask result = todoTaskServiceJPA.createTodoTask(todoTaskDTO,scheduleId);

        assertNotNull(result);
        assertEquals(schedule.getId(), result.getScheduleId());
        assertTrue(schedule.getTodoTasks().contains(result));

        verify(todoTaskRepository).save(todoTask);
        verify(scheduleRepository).findById(scheduleId);

    }
}