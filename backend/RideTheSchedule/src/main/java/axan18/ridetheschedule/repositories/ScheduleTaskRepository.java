package axan18.ridetheschedule.repositories;

import axan18.ridetheschedule.entities.ScheduleTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScheduleTaskRepository extends JpaRepository<ScheduleTask, UUID> {
}
