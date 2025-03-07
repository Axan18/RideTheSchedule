package axan18.ridetheschedule.repositories;

import axan18.ridetheschedule.entities.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
    Page<Schedule> findAllByAppUserIdAndDateBetweenOrderByDate(UUID userId, Date startDate, Date endDate, Pageable pageable);
    Optional<Schedule> findByDateAndAppUserId(Date date, UUID userId);
    Page<Schedule> findAllByAppUserIdAndDateBetween(UUID userId, Date startDate, Date endDate,  Pageable pageable);

}
