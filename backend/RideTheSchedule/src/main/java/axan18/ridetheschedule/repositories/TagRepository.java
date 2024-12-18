package axan18.ridetheschedule.repositories;

import axan18.ridetheschedule.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {

}
