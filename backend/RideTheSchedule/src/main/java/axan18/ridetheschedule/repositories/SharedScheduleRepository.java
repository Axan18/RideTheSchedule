package axan18.ridetheschedule.repositories;

import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.entities.SharedSchedule;
import axan18.ridetheschedule.models.SharedScheduleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SharedScheduleRepository extends JpaRepository<SharedSchedule, UUID> {
    @Query("SELECT DISTINCT ss.scheduleOwner FROM SharedSchedule ss " +
            "INNER JOIN ss.schedule s " +
            "WHERE ss.sharedWith.id = :myID AND FUNCTION('MONTH', s.date) = :month AND FUNCTION('YEAR', s.date) = :year")
    List<AppUser> findUsersWhoSharedSchedules(@Param("myID") UUID userID, @Param("month") int month, @Param("year") int year);
}

