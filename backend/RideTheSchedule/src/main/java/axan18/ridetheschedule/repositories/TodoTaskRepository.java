package axan18.ridetheschedule.repositories;

import axan18.ridetheschedule.entities.TodoTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TodoTaskRepository extends JpaRepository<TodoTask, UUID> {
}