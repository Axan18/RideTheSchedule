package axan18.ridetheschedule.repositories;

import axan18.ridetheschedule.entities.TodoTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TodoTaskRepository extends JpaRepository<TodoTask, UUID> {
    Page<TodoTask> findAllByScheduleId(UUID scheduleId, Pageable pageable);
}
