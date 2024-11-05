package axan18.ridetheschedule.repositories;
import axan18.ridetheschedule.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface UserRepository extends JpaRepository<AppUser, UUID> {
}
