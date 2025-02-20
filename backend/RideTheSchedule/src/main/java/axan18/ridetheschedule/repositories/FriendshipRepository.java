package axan18.ridetheschedule.repositories;

import axan18.ridetheschedule.entities.Friendship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface FriendshipRepository extends JpaRepository<Friendship, Friendship.FriendshipId> {

    @Transactional
    @Modifying
    @Query("UPDATE Friendship f set f.status = :status where f.id.id1 =:id1 and f.id.id2=:id2")
    void establishFriendship(@Param("id1") UUID sender, @Param("id2") UUID receiver, @Param("status") Friendship.Status status);

    @Query("SELECT f from Friendship f where f.id.id1= :id or f.id.id2= :id")
    Page<Friendship> getFriendshipsById(@Param("id") UUID id, Pageable pageable);

    @Query("SELECT f from Friendship f where f.id.id1= :id or f.id.id2= :id and f.status = :status")
    Page<Friendship> getFriendshipsByIdAndStatus(@Param("id") UUID id, @Param("status") Friendship.Status status, Pageable pageable);

    @Query("SELECT f from Friendship f where f.id.id1 =:id1 and f.id.id2=:id2 or f.id.id1=:id2 and f.id.id2=:id1")
    Optional<Friendship> getFriendshipBetween(@Param("id1") UUID sender, @Param("id2") UUID receiver);
}
