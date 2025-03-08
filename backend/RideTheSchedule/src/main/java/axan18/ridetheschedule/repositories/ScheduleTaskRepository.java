package axan18.ridetheschedule.repositories;

import axan18.ridetheschedule.entities.ScheduleTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface ScheduleTaskRepository extends JpaRepository<ScheduleTask, UUID> {
    @Transactional
    List<ScheduleTask> findAllByScheduleId(UUID scheduleId);
}
