package axan18.ridetheschedule.repositories;

import axan18.ridetheschedule.entities.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FriendRepository extends JpaRepository<Friend, UUID> {
}
