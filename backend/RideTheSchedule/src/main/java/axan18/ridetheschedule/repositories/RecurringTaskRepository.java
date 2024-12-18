package axan18.ridetheschedule.repositories;

import axan18.ridetheschedule.entities.RecurringTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecurringTaskRepository extends JpaRepository<RecurringTask, UUID> {
}
