package axan18.ridetheschedule.repositories;

import axan18.ridetheschedule.entities.SharedSchedule;
import axan18.ridetheschedule.models.SharedScheduleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface SharedScheduleRepository extends JpaRepository<SharedSchedule, UUID> {
}
