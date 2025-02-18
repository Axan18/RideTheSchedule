package axan18.ridetheschedule.repositories;
import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.entities.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    Page<AppUser> findAllByUsernameIsLikeIgnoreCase(String firstName, Pageable pageable);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE AppUser SET lastLoginDate = :lastLoginDate WHERE id = :userId")
    void updateLastLogin(@Param("userId") UUID userId, @Param("lastLoginDate") Date lastLoginDate);

    Optional<AppUser> findByEmail(String email);
}
