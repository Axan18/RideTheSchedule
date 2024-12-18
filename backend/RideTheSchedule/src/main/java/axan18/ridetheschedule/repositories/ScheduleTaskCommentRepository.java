package axan18.ridetheschedule.repositories;

import axan18.ridetheschedule.entities.ScheduleTaskComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScheduleTaskCommentRepository extends JpaRepository<ScheduleTaskComment, UUID> {
}
